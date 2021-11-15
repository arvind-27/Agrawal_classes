package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.AttendanceDao;
import com.example.agrawal_classes.dao.EmployeeDao;
import com.example.agrawal_classes.model.Attendance;
import com.example.agrawal_classes.model.Employee;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.utils.DateTimeUtil;
import com.example.agrawal_classes.utils.DateValidator;
import com.example.agrawal_classes.validators.AttendanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
public class AttendanceController {
    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DateValidator dateValidator;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AttendanceValidator attendanceValidator;

    @GetMapping("/admin/attendance")
    public String listAttendances(Model model, String date) {
        model.addAttribute("title", "Attendance Management");
        model.addAttribute("message", "View total attendance of each employee");
        if (date != null) {
            List<Attendance> attendance = attendanceDao.getAllByDate(Date.valueOf(date));
            model.addAttribute("attendances", attendance);
            return "attendance/listAttendanceDate";
        }
        List<Employee> employees = employeeDao.getAll();
        List<Map<String, Object>> daysPresent = attendanceDao.getAllEmployeeWisePresent();
        List<Map<String, Object>> daysAbsent = attendanceDao.getAllEmployeeWiseAbsent();

        model.addAttribute("employees", employees);
        model.addAttribute("daysPresent", daysPresent);
        model.addAttribute("daysAbsent", daysAbsent);
        return "attendance/listAttendanceEmployeeWise";
    }

    @GetMapping("/admin/attendance/{employeecode}")
    public String listAttendancesByEmployeeId(@PathVariable("employeecode") String employeecode, Model model) {
        String code = employeecode.substring(0, 2);
        // If user inputs date in place of employeecode
        if (dateValidator.isValid(employeecode, "yyyy-MM-dd")) {
            return "redirect:/admin/attendance?date=" + employeecode;
        }
        int employeeId = Integer.parseInt(employeecode.substring(2));
        String role = employeeDao.getRole(employeeId);
        if (!(code == "ES" && role == "ROLE_STAFF") && (code == "ET" && role == "ROLE_TEACHER")) {
            return "redirect:/admin/attendance";
        }
        model.addAttribute("title", "Attendance Management");
        model.addAttribute("message", "View all the attendance of particular employee");
        List<Attendance> attendance = attendanceDao.getAllByEmployeeId(employeeId);
        model.addAttribute("attendances", attendance);
        return "attendance/listAttendanceEmployee";
    }

    @GetMapping({ "/staff/attendance", "/teacher/attendance" })
    public String listOwnAttendance(Model model) {
        model.addAttribute("title", "Attendance Management");
        model.addAttribute("message", "View your attendance");
        int userId = securityService.findLoggedInUserId();
        int employeeId = employeeDao.getEmployeeIdByUserId(userId);
        List<Attendance> attendance = attendanceDao.getAllByEmployeeId(employeeId);
        model.addAttribute("attendances", attendance);
        return "attendance/listAttendanceEmployee";
    }

    @GetMapping("/staff/mark-attendance")
    public String markTeacherAttendance(Model model) {
        model.addAttribute("title", "Teachers' Attendance");
        model.addAttribute("message", "View teachers' today's attendance");
        String todayDate = dateTimeUtil.getCurrentDateTime("yyyy-MM-dd");
        model.addAttribute("date", todayDate);
        List<Attendance> attendance = attendanceDao.getAllByDateForTeacher(Date.valueOf(todayDate));
        model.addAttribute("attendances", attendance);
        return "attendance/listAttendanceTeacher";
    }

    @GetMapping("/staff/mark-attendance/{date}/ET{employeeId}/edit")
    public String editTeacherAttendance(@PathVariable("date") Date date, @PathVariable("employeeId") int employeeId,
            Model model) {
        String role = employeeDao.getRole(employeeId);
        if (!role.equals("ROLE_TEACHER"))
            throw new AccessDeniedException("You are not allowed to edit attendance of given employee");
        model.addAttribute("title", "Teachers' Attendance");
        model.addAttribute("message", "Edit teachers' today's attendance");
        model.addAttribute("submessage1", "Edit Attendance");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/staff/mark-attendance/" + date + "/ET" + employeeId + "/edit");
        model.addAttribute("edit", "true");
        Attendance attendance = attendanceDao.get(date, employeeId);
        if (attendance == null)
            return "redirect:/staff/mark-attendance";
        model.addAttribute("attendance", attendance);
        return "attendance/addEditAttendance";
    }

    @PostMapping("/staff/mark-attendance/{date}/ET{employeeId}/edit")
    public String editTeacherAttendance(@PathVariable("date") Date date, @PathVariable("employeeId") int employeeId,
            @Valid @ModelAttribute("attendance") Attendance attendance, BindingResult bindingResult, Model model) {
        String role = employeeDao.getRole(employeeId);
        if (!role.equals("ROLE_TEACHER"))
            throw new AccessDeniedException("You are not allowed to edit attendance of given employee");
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Teachers' Attendance");
            model.addAttribute("message", "Edit teachers' today's attendance");
            model.addAttribute("submessage1", "Edit Attendance");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/staff/mark-attendance/" + date + "/ET" + employeeId + "/edit");
            model.addAttribute("edit", "true");
            return "attendance/addEditAttendance";
        }
        Employee employee = attendance.getEmployee();
        employee.setEmployeeId(employeeId);
        attendance.setEmployee(employee);

        attendanceDao.update(attendance);
        return "redirect:/staff/mark-attendance";
    }

    @GetMapping("/admin/attendance/{date}/{employeecode}")
    public String redirectToListAttendancesByEmployeeId(@PathVariable("employeecode") String employeecode, Model model) {
        return "redirect:/admin/attendance/" + employeecode;
    }

    @GetMapping({ "/admin/attendance/add", "/staff/mark-attendance/add" })
    public String addAttendance(Model model) {
        model.addAttribute("title", "Attendance Management");
        model.addAttribute("message", "Add Attendance");
        model.addAttribute("submessage1", "Add Attendance");
        model.addAttribute("buttonmessage", "Finish");
        String role = securityService.findLoggedInUserRole();
        List<Employee> employees;
        if (role.equals("admin")) {
            model.addAttribute("submiturl", "/admin/attendance/add");
            employees = employeeDao.getAll();
        } else {
            model.addAttribute("submiturl", "/staff/mark-attendance/add");
            employees = employeeDao.getAllTeachers();
        }
        model.addAttribute("employees", employees);
        Attendance attendance = new Attendance();
        String todayDate = dateTimeUtil.getCurrentDateTime("yyyy-MM-dd");
        attendance.setDate(Date.valueOf(todayDate));
        attendance.setIsPresent(true);
        model.addAttribute("attendance", attendance);
        return "attendance/addEditAttendance";
    }

    @PostMapping("/admin/attendance/add")
    public String addAttendance(@Valid @ModelAttribute("attendance") Attendance attendance, BindingResult bindingResult, Model model) {
        attendanceValidator.validate(attendance, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Attendance Management");
            model.addAttribute("message", "Add Attendance");
            model.addAttribute("submessage1", "Add Attendance");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/attendance/add");
            List<Employee> employees = employeeDao.getAll();
            model.addAttribute("employees", employees);
            return "attendance/addEditAttendance";
        }
        attendanceDao.save(attendance);
        return "redirect:/admin/attendance";
    }

    @PostMapping("/staff/mark-attendance/add")
    public String addTeacherAttendance(@Valid @ModelAttribute("attendance") Attendance attendance, BindingResult bindingResult,
            Model model) {
        String todayDate = dateTimeUtil.getCurrentDateTime("yyyy-MM-dd");
        attendance.setDate(Date.valueOf(todayDate));
        attendanceValidator.validate(attendance, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Attendance Management");
            model.addAttribute("message", "Add Attendance");
            model.addAttribute("submessage1", "Add Attendance");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/staff/mark-attendance/add");
            List<Employee> employees = employeeDao.getAllTeachers();
            model.addAttribute("employees", employees);
            return "attendance/addEditAttendance";
        }
        attendanceDao.save(attendance);
        return "redirect:/staff/mark-attendance";
    }

    @GetMapping("/admin/attendance/{date}/{employeecode}/edit")
    public String editAttendance(@PathVariable("date") Date date, @PathVariable("employeecode") String employeecode, Model model) {
        String code = employeecode.substring(0, 2);
        int employeeId = Integer.parseInt(employeecode.substring(2));
        String role = employeeDao.getRole(employeeId);
        if (!(code == "ES" && role == "ROLE_STAFF") && (code == "ET" && role == "ROLE_TEACHER")) {
            return "redirect:/admin/attendance";
        }
        model.addAttribute("title", "Attendance Management");
        model.addAttribute("message", "Edit Attendance");
        model.addAttribute("submessage1", "Edit Attendance");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/attendance/" + date + "/" + code + employeeId + "/edit");
        model.addAttribute("edit", "true");
        Attendance attendance = attendanceDao.get(date, employeeId);
        model.addAttribute("attendance", attendance);
        return "attendance/addEditAttendance";
    }

    @PostMapping("/admin/attendance/{date}/{employeecode}/edit")
    public String editAttendance(@PathVariable("date") Date date, @PathVariable("employeecode") String employeecode,
            @Valid @ModelAttribute("attendance") Attendance attendance, BindingResult bindingResult, Model model) {
        String code = employeecode.substring(0, 2);
        int employeeId = Integer.parseInt(employeecode.substring(2));
        String role = employeeDao.getRole(employeeId);
        if (!(code == "ES" && role == "ROLE_STAFF") && (code == "ET" && role == "ROLE_TEACHER")) {
            return "redirect:/admin/attendance";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Attendance Management");
            model.addAttribute("message", "Edit Attendance");
            model.addAttribute("submessage1", "Edit Attendance");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/attendance/" + date + "/" + code + employeeId + "/edit");
            model.addAttribute("edit", "true");
            return "attendance/addEditAttendance";
        }
        Employee employee = attendance.getEmployee();
        employee.setEmployeeId(employeeId);
        attendance.setEmployee(employee);

        attendanceDao.update(attendance);
        return "redirect:/admin/attendance/" + employeecode;
    }

    @GetMapping("/admin/attendance/{date}/{employeeId}/delete")
    public ResponseEntity<Integer> deleteAttendance(@PathVariable("date") Date date,
            @PathVariable("employeeId") int employeeId, Model model) {
        attendanceDao.delete(date, employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
