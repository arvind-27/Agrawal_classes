package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.*;
import com.example.agrawal_classes.model.*;
import com.example.agrawal_classes.services.UserService;
import com.example.agrawal_classes.validators.StaffValidator;
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
public class StaffController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserPhoneNumberDao userPhoneNumberDao;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffValidator staffValidator;

    @Autowired
    private BatchDao batchDao;

    @GetMapping("/admin/staffs")
    public String staffsPortal(Model model) {
        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "View all the staffs");
        List<Staff> staffs = staffDao.getAll();
        model.addAttribute("staffs", staffs);
        return "staff/staffsPortal";
    }

    @GetMapping("/admin/staffs/add")
    public String addStaff(Model model) {
        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Create Staff's profile");
        model.addAttribute("submessage1", "Add Staff Details");
        model.addAttribute("submessage2", "Step 1: Staff Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/admin/staffs/add");
        Staff staff = new Staff();
        model.addAttribute("staff", staff);
        return "staff/addEditStaff";
    }

    @PostMapping("/admin/staffs/add")
    public String addStaff(@Valid @ModelAttribute("staff") Staff staff, BindingResult bindingResult, Model model) {
        staffValidator.validate(staff.getEmployee().getUser(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Staff Portal");
            model.addAttribute("message", "Create Staff's profile");
            model.addAttribute("submessage1", "Add Staff Details");
            model.addAttribute("submessage2", "Step 1: Staff Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/admin/staffs/add");
            return "staff/addEditStaff";
        }

        Employee employee = staff.getEmployee();
        User user = employee.getUser();
        user.setRole("ROLE_STAFF");
        user = userService.save(user);
        user = userService.activateUserAndEmailToken(user);

        employee.setUser(user);
        employee = employeeDao.save(employee);

        staff.setEmployee(employee);
        staff = staffDao.save(staff);

        return "redirect:/admin/staffs/ES" + staff.getEmployee().getEmployeeId() + "/add-staff-phone";
    }

    @GetMapping("/admin/staffs/ES{employeeId}")
    public String viewStaff(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "View Staff's profile");
        model.addAttribute("submessage1", "Staff Details");
        Staff staff = staffDao.getByEmployeeId(employeeId);
        int userId = staff.getEmployee().getUser().getUserId();
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<Batch> batchesAssigned = batchDao.getAllByStaffId(staff.getStaffId());
        List<Batch> batches = batchDao.getAll();
        model.addAttribute("staff", staff);
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

        return "staff/viewStaff";
    }

    @GetMapping("/admin/staffs/ES{employeeId}/edit-staff")
    public String editStaff(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Edit Staff's profile");
        model.addAttribute("submessage1", "Edit Staff Details");
        model.addAttribute("submessage2", "Step 1: Staff Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/admin/staffs/ES" + employeeId + "/edit-staff");
        model.addAttribute("edit", "true");
        Staff staff = staffDao.getByEmployeeId(employeeId);
        model.addAttribute("staff", staff);
        return "staff/addEditStaff";
    }

    @PostMapping("/admin/staffs/ES{employeeId}/edit-staff")
    public String editStaff(@PathVariable("employeeId") int employeeId, @Valid @ModelAttribute("staff") Staff staff,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Staff Portal");
            model.addAttribute("message", "Edit Staff's profile");
            model.addAttribute("submessage1", "Edit Staff Details");
            model.addAttribute("submessage2", "Step 1: Staff Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/admin/staffs/ES" + employeeId + "/edit-staff");
            model.addAttribute("edit", "true");
            return "staff/addEditStaff";
        }
        Staff oldStaff = staffDao.getByEmployeeId(employeeId);

        Employee employee = staff.getEmployee();
        User user = employee.getUser();
        user.setUserId(oldStaff.getEmployee().getUser().getUserId());

        employee.setUser(user);
        employee.setEmployeeId(oldStaff.getEmployee().getEmployeeId());
        staff.setEmployee(employee);
        staff.setStaffId(oldStaff.getStaffId());

        userDao.update(user);
        employeeDao.update(employee);
        staffDao.update(staff);
        return "redirect:/admin/staffs/ES{employeeId}/edit-staff-phone";
    }

    @GetMapping("/admin/staffs/ES{employeeId}/add-staff-phone")
    public String addStaffPhone(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Create Staff's profile");
        model.addAttribute("submessage1", "Add Staff Details");
        model.addAttribute("submessage2", "Step 2: Staff Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        Employee employee = employeeDao.get(employeeId);
        int userId = employee.getUser().getUserId();
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "staff/addEditStaffPhone";
    }

    @GetMapping("/admin/staffs/ES{employeeId}/edit-staff-phone")
    public String editStaffPhone(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Edit Staff's profile");
        model.addAttribute("submessage1", "Edit Staff Details");
        model.addAttribute("submessage2", "Step 2: Staff Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        Employee employee = employeeDao.get(employeeId);
        int userId = employee.getUser().getUserId();
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "staff/addEditStaffPhone";
    }

}
