package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.*;
import com.example.agrawal_classes.model.*;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Transactional
@Controller
public class ProfileController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GuardianDao guardianDao;

    @Autowired
    private UserPhoneNumberDao userPhoneNumberDao;

    @Autowired
    private GuardianPhoneNumberDao guardianPhoneNumberDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @GetMapping("/profile")
    public String redirectToProfile(Model model) {
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            return "redirect:/profile/student";
        }
        else if (role.equals("teacher")) {
            return "redirect:/profile/teacher";
        }
        else if (role.equals("staff")) {
            return "redirect:/profile/staff";
        }
        else {
            return "redirect:/profile/admin";
        }
    }

    @GetMapping("/profile/teacher/add")
    public String addTeacher(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId != null)
            return "redirect:/profile";

        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Create Teacher's profile");
        model.addAttribute("submessage1", "Add Teacher Details");
        model.addAttribute("submessage2", "Step 1: Teacher Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/profile/teacher/add");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);

        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teacher/addEditTeacher";
    }

    @PostMapping("/profile/teacher/add")
    public String addTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult, Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId != null)
            return "redirect:/profile";

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Teacher Portal");
            model.addAttribute("message", "Create Teacher's profile");
            model.addAttribute("submessage1", "Add Teacher Details");
            model.addAttribute("submessage2", "Step 1: Teacher Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/profile/teacher/add");
            List<Subject> subjects = subjectDao.getAll();
            model.addAttribute("subjects", subjects);
            return "teacher/addEditTeacher";
        }
        Employee employee = teacher.getEmployee();
        User user = employee.getUser();
        user.setUserId(userId);

        employee.setUser(user);
        employee = employeeDao.save(employee);

        teacher.setEmployee(employee);
        teacher = teacherDao.save(teacher);

        return "redirect:/profile/teacher/add-teacher-phone";
    }

    @GetMapping("/profile/teacher")
    public String viewTeacher(Model model, String changeSuccessful) {
        if (changeSuccessful != null)
            model.addAttribute("successmessage", "Your password has been changed successfully");
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/teacher/add";

        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "View Teacher's profile");
        model.addAttribute("submessage1", "Teacher Details");
        Teacher teacher = teacherDao.getByEmployeeId(employeeId);
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("userPhoneNumbers", userPhoneNumbers);
        return "teacher/viewTeacher";
    }

    @GetMapping("/profile/teacher/edit-teacher")
    public String editTeacher(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/teacher/add";

        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Edit Teacher's profile");
        model.addAttribute("submessage1", "Edit Teacher Details");
        model.addAttribute("submessage2", "Step 1: Teacher Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/profile/teacher/edit-teacher");
        model.addAttribute("edit", "true");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);

        Teacher teacher = teacherDao.getByEmployeeId(employeeId);
        model.addAttribute("teacher", teacher);
        return "teacher/addEditTeacher";
    }

    @PostMapping("/profile/teacher/edit-teacher")
    public String editTeacher(@Valid @ModelAttribute("teacher") Teacher teacher,
            BindingResult bindingResult, Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/teacher/add";

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Teacher Portal");
            model.addAttribute("message", "Edit Teacher's profile");
            model.addAttribute("submessage1", "Edit Teacher Details");
            model.addAttribute("submessage2", "Step 1: Teacher Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/profile/teacher/edit-teacher");
            model.addAttribute("edit", "true");
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
        teacher.setSubject(oldTeacher.getSubject());

        employeeDao.updateOwnProfile(employee);
        teacherDao.update(teacher);
        return "redirect:/profile/teacher/edit-teacher-phone";
    }

    @GetMapping("/profile/teacher/add-teacher-phone")
    public String addTeacherPhone(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/teacher/add";

        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Create Teacher's profile");
        model.addAttribute("submessage1", "Add Teacher Details");
        model.addAttribute("submessage2", "Step 2: Teacher Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "teacher/addEditTeacherPhone";
    }

    @GetMapping("/profile/teacher/edit-teacher-phone")
    public String editTeacherPhone(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("teacher"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/teacher/add";

        model.addAttribute("title", "Teacher Portal");
        model.addAttribute("message", "Edit Teacher's profile");
        model.addAttribute("submessage1", "Edit Teacher Details");
        model.addAttribute("submessage2", "Step 2: Teacher Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "teacher/addEditTeacherPhone";
    }

    @GetMapping("/profile/staff/add")
    public String addStaff(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId != null)
            return "redirect:/profile";

        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Create Staff's profile");
        model.addAttribute("submessage1", "Add Staff Details");
        model.addAttribute("submessage2", "Step 1: Staff Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/profile/staff/add");
        Staff staff = new Staff();
        model.addAttribute("staff", staff);
        return "staff/addEditStaff";
    }

    @PostMapping("/profile/staff/add")
    public String addStaff(@Valid @ModelAttribute("staff") Staff staff, BindingResult bindingResult, Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId != null)
            return "redirect:/profile";

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Staff Portal");
            model.addAttribute("message", "Create Staff's profile");
            model.addAttribute("submessage1", "Add Staff Details");
            model.addAttribute("submessage2", "Step 1: Staff Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/profile/staff/add");
            return "staff/addEditStaff";
        }

        Employee employee = staff.getEmployee();
        User user = employee.getUser();
        user.setUserId(userId);

        employee.setUser(user);
        employee = employeeDao.save(employee);

        staff.setEmployee(employee);
        staff = staffDao.save(staff);

        return "redirect:/profile/staff/add-staff-phone";
    }

    @GetMapping("/profile/staff")
    public String viewStaff(Model model, String changeSuccessful) {
        if (changeSuccessful != null)
            model.addAttribute("successmessage", "Your password has been changed successfully");
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/staff/add";

        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "View Staff's profile");
        model.addAttribute("submessage1", "Staff Details");
        Staff staff = staffDao.getByEmployeeId(employeeId);
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("staff", staff);
        model.addAttribute("userPhoneNumbers", userPhoneNumbers);
        return "staff/viewStaff";
    }

    @GetMapping("/profile/staff/edit-staff")
    public String editStaff(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/staff/add";

        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Edit Staff's profile");
        model.addAttribute("submessage1", "Edit Staff Details");
        model.addAttribute("submessage2", "Step 1: Staff Details");
        model.addAttribute("buttonmessage", "Proceed to Step 2");
        model.addAttribute("submiturl", "/profile/staff/edit-staff");
        model.addAttribute("edit", "true");
        Staff staff = staffDao.getByEmployeeId(employeeId);
        model.addAttribute("staff", staff);
        return "staff/addEditStaff";
    }

    @PostMapping("/profile/staff/edit-staff")
    public String editStaff(@Valid @ModelAttribute("staff") Staff staff,
            BindingResult bindingResult, Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/staff/add";

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Staff Portal");
            model.addAttribute("message", "Edit Staff's profile");
            model.addAttribute("submessage1", "Edit Staff Details");
            model.addAttribute("submessage2", "Step 1: Staff Details");
            model.addAttribute("buttonmessage", "Proceed to Step 2");
            model.addAttribute("submiturl", "/profile/staff/edit-staff");
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

        employeeDao.updateOwnProfile(employee);
        staffDao.update(staff);
        return "redirect:/profile/staff/edit-staff-phone";
    }

    @GetMapping("/profile/staff/add-staff-phone")
    public String addStaffPhone(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/staff/add";

        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Create Staff's profile");
        model.addAttribute("submessage1", "Add Staff Details");
        model.addAttribute("submessage2", "Step 2: Staff Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "staff/addEditStaffPhone";
    }

    @GetMapping("/profile/staff/edit-staff-phone")
    public String editStaffPhone(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("staff"))
            return "redirect:/profile";
        Integer employeeId = employeeDao.getEmployeeIdByUserId(userId);
        if (employeeId == null)
            return "redirect:/profile/staff/add";

        model.addAttribute("title", "Staff Portal");
        model.addAttribute("message", "Edit Staff's profile");
        model.addAttribute("submessage1", "Edit Staff Details");
        model.addAttribute("submessage2", "Step 2: Staff Phone Number");
        model.addAttribute("buttonmessage", "Finish");
        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("userId", userId);
        return "staff/addEditStaffPhone";
    }

    @GetMapping("/profile/student/add")
    public String addStudent(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId != null)
            return "redirect:/profile";

        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Create Student's profile");
        model.addAttribute("submessage1", "Add Student Details");
        model.addAttribute("buttonmessage", "Proceed");
        model.addAttribute("submiturl", "/profile/student/add");
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/addEditStudent";
    }

    @PostMapping("/profile/student/add")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        System.out.println(bindingResult);
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId != null)
            return "redirect:/profile";

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Student Portal");
            model.addAttribute("message", "Create Student's profile");
            model.addAttribute("submessage1", "Add Student Details");
            model.addAttribute("buttonmessage", "Proceed");
            model.addAttribute("submiturl", "/profile/student/add");
            return "student/addEditStudent";
        }
        User user = student.getUser();
        user.setUserId(userId);

        student.setUser(user);
        student = studentDao.save(student);

        Guardian guardian = student.getGuardian();
        guardian.setStudentId(student.getStudentId());
        guardianDao.save(guardian);

        return "redirect:/profile/student/add-student-phone";
    }

    @GetMapping("/profile/student")
    public String viewStudent(Model model, String changeSuccessful) {
        if (changeSuccessful != null)
            model.addAttribute("successmessage", "Your password has been changed successfully");
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId == null)
            return "redirect:/profile/student/add";

        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "View Student's profile");
        model.addAttribute("submessage1", "Student Details");
        Student student = studentDao.get(studentId);
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<GuardianPhoneNumber> guardianPhoneNumbers = guardianPhoneNumberDao.getByStudentId(studentId);
        model.addAttribute("student", student);
        model.addAttribute("userPhoneNumbers", userPhoneNumbers);
        model.addAttribute("guardianPhoneNumbers", guardianPhoneNumbers);
        return "student/viewStudent";
    }

    @GetMapping("/profile/student/edit-student")
    public String editStudent(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId == null)
            return "redirect:/profile/student/add";

        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Edit Student's profile");
        model.addAttribute("submessage1", "Edit Student Details");
        model.addAttribute("buttonmessage", "Proceed");
        model.addAttribute("submiturl", "/profile/student/edit-student");
        model.addAttribute("edit", "true");
        Student student = studentDao.get(studentId);
        model.addAttribute("student", student);
        return "student/addEditStudent";
    }

    @PostMapping("/profile/student/edit-student")
    public String editStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId == null)
            return "redirect:/profile/student/add";

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Student Portal");
            model.addAttribute("message", "Edit Student's profile");
            model.addAttribute("submessage1", "Edit Student Details");
            model.addAttribute("submiturl", "/profile/student/edit-student");
            model.addAttribute("buttonmessage", "Proceed");
            model.addAttribute("edit", "true");
            return "student/addEditStudent";
        }
        User user = student.getUser();
        user.setUserId(userId);

        student.setUser(user);
        studentDao.update(student);

        Guardian guardian = student.getGuardian();
        guardian.setStudentId(studentId);
        guardianDao.update(guardian);
        return "redirect:/profile/student/edit-student-phone";
    }

    @GetMapping("/profile/student/add-student-phone")
    public String addStudentPhone(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId == null)
            return "redirect:/profile/student/add";

        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Create Student's profile");
        model.addAttribute("submessage1", "Add Student Details");
        model.addAttribute("buttonmessage", "Finish");
        List<UserPhoneNumber> studentPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<GuardianPhoneNumber> guardianPhoneNumbers = guardianPhoneNumberDao.getByStudentId(studentId);
        model.addAttribute("studentPhoneNumbers", studentPhoneNumbers);
        model.addAttribute("guardianPhoneNumbers", guardianPhoneNumbers);
        model.addAttribute("userId", userId);
        return "student/addEditStudentPhone";
    }

    @GetMapping("/profile/student/edit-student-phone")
    public String editStudentPhone(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("student"))
            return "redirect:/profile";
        Integer studentId = studentDao.getStudentIdByUserId(userId);
        if (studentId == null)
            return "redirect:/profile/student/add";

        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Edit Student's profile");
        model.addAttribute("submessage1", "Edit Student Details");
        model.addAttribute("buttonmessage", "Finish");
        List<UserPhoneNumber> studentPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<GuardianPhoneNumber> guardianPhoneNumbers = guardianPhoneNumberDao.getByStudentId(studentId);
        model.addAttribute("studentPhoneNumbers", studentPhoneNumbers);
        model.addAttribute("guardianPhoneNumbers", guardianPhoneNumbers);
        model.addAttribute("userId", userId);
        return "student/addEditStudentPhone";
    }

    @PostMapping("/profile/student/add-guardian-phone")
    public ResponseEntity<String> addGuardianPhoneNumber(@RequestParam String phoneNumber, Model model) {
        if (!phoneNumber.matches("^[1-9][0-9]{9,9}$")) {
            return new ResponseEntity<>("The phone number must be of 10 digits", HttpStatus.BAD_REQUEST);
        }
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        String guardianName = guardianDao.getNameByStudentId(studentId);
        GuardianPhoneNumber guardianPhoneNumber = new GuardianPhoneNumber(phoneNumber, guardianName, studentId);
        try {
            guardianPhoneNumberDao.save(guardianPhoneNumber);
        } catch (Exception e) {
            return new ResponseEntity<>("The phone number already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/student/delete-guardian-phone")
    public ResponseEntity<Integer> deleteGuardianPhoneNumber(@RequestParam String phoneNumber, Model model) {
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        String guardianName = guardianDao.getNameByStudentId(studentId);
        GuardianPhoneNumber guardianPhoneNumber = new GuardianPhoneNumber(phoneNumber, guardianName, studentId);
        guardianPhoneNumberDao.delete(guardianPhoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile/change-password")
    public String changePassword(Model model) {
        model.addAttribute("title", "Change Password");
        model.addAttribute("message", "Change your password");
        model.addAttribute("submitUrl", "/profile/change-password");
        return "/user/changePassword";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("oldPassword") String oldPassword, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("title", "Change Password");
            model.addAttribute("message", "Change your password");
            model.addAttribute("errormessage", "The passwords do not match");
            model.addAttribute("submitUrl", "/profile/change-password");
            return "user/changePassword";
        }
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!userService.verifyOldPassword(userId, oldPassword)) {
            model.addAttribute("title", "Change Password");
            model.addAttribute("message", "Change your password");
            model.addAttribute("errormessage", "Old Password is incorrect");
            model.addAttribute("submitUrl", "/profile/change-password");
            return "user/changePassword";
        }
        userService.changePassword(userId, password);
        return "redirect:/profile/" + role + "?changeSuccessful";
    }

    @GetMapping("/profile/admin")
    public String viewAdmin(Model model, String changeSuccessful) {
        if (changeSuccessful != null)
            model.addAttribute("successmessage", "Your password has been changed successfully");
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        User user = userDao.get(userId);
        if (!role.equals("admin"))
            return "redirect:/profile";
        model.addAttribute("title", "Admin Portal");
        model.addAttribute("message", "View Admin profile");
        model.addAttribute("submessage1", "Admin Details");
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("userPhoneNumbers", userPhoneNumbers);
        return "user/viewAdmin";
    }

}
