package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.*;
import com.example.agrawal_classes.model.*;
import com.example.agrawal_classes.services.UserService;
import com.example.agrawal_classes.validators.TeacherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class TeacherController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private UserPhoneNumberDao userPhoneNumberDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherValidator teacherValidator;

    @Autowired
    private BatchDao batchDao;

    @GetMapping("/admin/teachers")
    public String teachersPortal(Model model) {
        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "View all the teachers");
        List<Teacher> teachers = teacherDao.getAll();
        model.addAttribute("teachers", teachers);
        return "teacher/teachersPortal";
    }

    @GetMapping("/admin/teachers/add")
    public String addTeacher(Model model) {
        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Create Teacher's profile");
        model.addAttribute("submessage1", "Add Teacher Details");
        model.addAttribute("submessage2", "Step 1: Teacher Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/admin/teachers/add");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);

        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teacher/addEditTeacher";
    }

    @PostMapping("/admin/teachers/add")
    public String addTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult, Model model) {
        teacherValidator.validate(teacher.getEmployee().getUser(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Teacher Portal");
            model.addAttribute("message", "Create Teacher's profile");
            model.addAttribute("submessage1", "Add Teacher Details");
            model.addAttribute("submessage2", "Step 1: Teacher Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/admin/teachers/add");
            List<Subject> subjects = subjectDao.getAll();
            model.addAttribute("subjects", subjects);
            return "teacher/addEditTeacher";
        }
        Employee employee = teacher.getEmployee();
        User user = employee.getUser();
        user.setRole("ROLE_TEACHER");
        user = userService.save(user);
        user = userService.activateUserAndEmailToken(user);

        employee.setUser(user);
        employee = employeeDao.save(employee);

        teacher.setEmployee(employee);
        teacher = teacherDao.save(teacher);

        return "redirect:/admin/teachers/ET" + teacher.getEmployee().getEmployeeId() + "/add-teacher-phone";
    }

    @GetMapping("/admin/teachers/ET{employeeId}")
    public String viewTeacher(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "View Teacher's profile");
        model.addAttribute("submessage1", "Teacher Details");
        Teacher teacher = teacherDao.getByEmployeeId(employeeId);
        int userId = teacher.getEmployee().getUser().getUserId();
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<Batch> batchesAssigned = batchDao.getAllByTeacherId(teacher.getTeacherId());
        List<Batch> batches = batchDao.getAllBySubjectId(teacher.getSubject().getSubjectId());
        model.addAttribute("teacher", teacher);
        model.addAttribute("userPhoneNumbers", userPhoneNumbers);
        model.addAttribute("batches", batches);

        List<Boolean> isAssigned = new ArrayList<>();
        for (Batch batch : batches) {
            boolean assigned = false;
            for (Batch batchAssigned : batchesAssigned) {
                if (batchAssigned.equals(batch)) {
                    assigned = true;
                    break;
                }
            }
            isAssigned.add(assigned);
        }
        model.addAttribute("isAssigned", isAssigned);

        return "teacher/viewTeacher";
    }

    @GetMapping("/admin/teachers/ET{employeeId}/edit-teacher")
    public String editTeacher(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Edit Teacher's profile");
        model.addAttribute("submessage1", "Edit Teacher Details");
        model.addAttribute("submessage2", "Step 1: Teacher Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/admin/teachers/ET" + employeeId + "/edit-teacher");
        model.addAttribute("edit", "true");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);

        Teacher teacher = teacherDao.getByEmployeeId(employeeId);
        model.addAttribute("teacher", teacher);
        return "teacher/addEditTeacher";
    }

    @PostMapping("/admin/teachers/ET{employeeId}/edit-teacher")
    public String editTeacher(@PathVariable("employeeId") int employeeId, @Valid @ModelAttribute("teacher") Teacher teacher,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Teacher Portal");
            model.addAttribute("message", "Edit Teacher's profile");
            model.addAttribute("submessage1", "Edit Teacher Details");
            model.addAttribute("submessage2", "Step 1: Teacher Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/admin/teachers/ET" + employeeId + "/edit-teacher");
            model.addAttribute("edit", "true");
            List<Subject> subjects = subjectDao.getAll();
            model.addAttribute("subjects", subjects);
            return "teacher/addEditTeacher";
        }
        Teacher oldTeacher = teacherDao.getByEmployeeId(employeeId);

        Employee employee = teacher.getEmployee();
        User user = employee.getUser();
        user.setUserId(oldTeacher.getEmployee().getUser().getUserId());

        employee.setUser(user);
        employee.setEmployeeId(oldTeacher.getEmployee().getEmployeeId());
        teacher.setEmployee(employee);
        teacher.setTeacherId(oldTeacher.getTeacherId());

        userDao.update(user);
        employeeDao.update(employee);
        teacherDao.update(teacher);
        return "redirect:/admin/teachers/ET{employeeId}/edit-teacher-phone";
    }

    @GetMapping("/admin/teachers/ET{employeeId}/add-teacher-phone")
    public String addTeacherPhone(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Create Teacher's profile");
        model.addAttribute("submessage1", "Add Teacher Details");
        model.addAttribute("submessage2", "Step 2: Teacher Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        Employee employee = employeeDao.get(employeeId);
        int userId = employee.getUser().getUserId();
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "teacher/addEditTeacherPhone";
    }

    @GetMapping("/admin/teachers/ET{employeeId}/edit-teacher-phone")
    public String editTeacherPhone(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Edit Teacher's profile");
        model.addAttribute("submessage1", "Edit Teacher Details");
        model.addAttribute("submessage2", "Step 2: Teacher Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        Employee employee = employeeDao.get(employeeId);
        int userId = employee.getUser().getUserId();
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "teacher/addEditTeacherPhone";
    }

}
