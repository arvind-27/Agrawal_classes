package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.*;
import com.example.agrawal_classes.model.*;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.services.UserService;
import com.example.agrawal_classes.validators.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Transactional
@Controller
public class StudentController {
    @Autowired
    private UserDao userDao;

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
    private SecurityService securityService;

    @Autowired
    private StudentValidator studentValidator;

    @GetMapping({ "/admin/students", "/staff/students" })
    public String studentsPortal(Model model) {
        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "View all the students");
        List<Student> students = studentDao.getAll();
        model.addAttribute("students", students);
        return "student/studentsPortal";
    }

    @GetMapping({ "/admin/students/add", "/staff/students/add" })
    public String addStudent(Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Create Student's profile");
        model.addAttribute("submessage1", "Add Student Details");
        model.addAttribute("buttonmessage", "Proceed");
        model.addAttribute("submiturl", "/" + role + "/students/add");
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/addEditStudent";
    }

    @PostMapping({ "/admin/students/add", "/staff/students/add" })
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        studentValidator.validate(student.getUser(), bindingResult);
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Student Portal");
            model.addAttribute("message", "Create Student's profile");
            model.addAttribute("submessage1", "Add Student Details");
            model.addAttribute("buttonmessage", "Proceed");
            model.addAttribute("submiturl", "/" + role + "/students/add");
            return "student/addEditStudent";
        }

        User user = student.getUser();
        user.setRole("ROLE_STUDENT");
        user = userService.save(user);
        user = userService.activateUserAndEmailToken(user);

        student.setUser(user);
        student = studentDao.save(student);

        Guardian guardian = student.getGuardian();
        guardian.setStudentId(student.getStudentId());
        guardianDao.save(guardian);

        return "redirect:/" + role + "/students/ST" + student.getStudentId() + "/add-student-phone";
    }

    @GetMapping({ "/admin/students/ST{studentId}", "/staff/students/ST{studentId}" })
    public String viewStudent(@PathVariable("studentId") int studentId, Model model) {
        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "View Student's profile");
        model.addAttribute("submessage1", "Student Details");
        Student student = studentDao.get(studentId);
        int userId = student.getUser().getUserId();
        List<UserPhoneNumber> userPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<GuardianPhoneNumber> guardianPhoneNumbers = guardianPhoneNumberDao.getByStudentId(studentId);
        model.addAttribute("student", student);
        model.addAttribute("userPhoneNumbers", userPhoneNumbers);
        model.addAttribute("guardianPhoneNumbers", guardianPhoneNumbers);
        return "student/viewStudent";
    }

    @GetMapping({ "/admin/students/ST{studentId}/edit-student", "/staff/students/ST{studentId}/edit-student" })
    public String editStudent(@PathVariable("studentId") int studentId, Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Edit Student's profile");
        model.addAttribute("submessage1", "Edit Student Details");
        model.addAttribute("buttonmessage", "Proceed");
        model.addAttribute("submiturl", "/" + role + "/students/ST" + studentId + "/edit-student");
        model.addAttribute("edit", "true");
        Student student = studentDao.get(studentId);
        model.addAttribute("student", student);
        return "student/addEditStudent";
    }

    @PostMapping({ "/admin/students/ST{studentId}/edit-student", "/staff/students/ST{studentId}/edit-student" })
    public String editStudent(@PathVariable("studentId") int studentId, @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult, Model model) {
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Student Portal");
            model.addAttribute("message", "Edit Student's profile");
            model.addAttribute("submessage1", "Edit Student Details");
            model.addAttribute("submiturl", "/" + role + "/students/ST" + studentId + "/edit-student");
            model.addAttribute("buttonmessage", "Proceed");
            model.addAttribute("edit", "true");
            return "student/addEditStudent";
        }
        Student oldStudent = studentDao.get(studentId);

        User user = student.getUser();
        user.setUserId(oldStudent.getUser().getUserId());

        student.setUser(user);

        userDao.update(user);
        studentDao.update(student);

        Guardian guardian = student.getGuardian();
        guardian.setStudentId(studentId);
        guardianDao.update(guardian);
        return "redirect:/" + role + "/students/ST{studentId}/edit-student-phone";
    }

    @GetMapping({ "/admin/students/ST{studentId}/add-student-phone",
            "/staff/students/ST{studentId}/add-student-phone" })
    public String addStudentPhone(@PathVariable("studentId") int studentId, Model model) {
        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Create Student's profile");
        model.addAttribute("submessage1", "Add Student Details");
        model.addAttribute("buttonmessage", "Finish");
        Student student = studentDao.get(studentId);
        int userId = student.getUser().getUserId();
        List<UserPhoneNumber> studentPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<GuardianPhoneNumber> guardianPhoneNumbers = guardianPhoneNumberDao.getByStudentId(studentId);
        model.addAttribute("studentPhoneNumbers", studentPhoneNumbers);
        model.addAttribute("guardianPhoneNumbers", guardianPhoneNumbers);
        model.addAttribute("userId", userId);
        return "student/addEditStudentPhone";
    }

    @GetMapping({ "/admin/students/ST{studentId}/edit-student-phone", "/staff/students/ST{studentId}/edit-student-phone" })
    public String editStudentPhone(@PathVariable("studentId") int studentId, Model model) {
        model.addAttribute("title", "Student Portal");
        model.addAttribute("message", "Edit Student's profile");
        model.addAttribute("submessage1", "Edit Student Details");
        model.addAttribute("buttonmessage", "Finish");
        Student student = studentDao.get(studentId);
        int userId = student.getUser().getUserId();
        List<UserPhoneNumber> studentPhoneNumbers = userPhoneNumberDao.getByUserId(userId);
        List<GuardianPhoneNumber> guardianPhoneNumbers = guardianPhoneNumberDao.getByStudentId(studentId);
        model.addAttribute("studentPhoneNumbers", studentPhoneNumbers);
        model.addAttribute("guardianPhoneNumbers", guardianPhoneNumbers);
        model.addAttribute("userId", userId);
        return "student/addEditStudentPhone";
    }

    @PostMapping({ "/admin/students/ST{studentId}/add-guardian-phone", "/staff/students/ST{studentId}/add-guardian-phone" })
    public ResponseEntity<String> addGuardianPhoneNumber(@PathVariable("studentId") int studentId,
            @RequestParam String phoneNumber, Model model) {
        if (!phoneNumber.matches("^[1-9][0-9]{9,9}$")) {
            return new ResponseEntity<>("The phone number must be of 10 digits", HttpStatus.BAD_REQUEST);
        }
        String guardianName = guardianDao.getNameByStudentId(studentId);
        GuardianPhoneNumber guardianPhoneNumber = new GuardianPhoneNumber(phoneNumber, guardianName, studentId);
        try {
            guardianPhoneNumberDao.save(guardianPhoneNumber);
        } catch (Exception e) {
            return new ResponseEntity<>("The phone number already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({ "/admin/students/ST{studentId}/delete-guardian-phone", "/staff/students/ST{studentId}/delete-guardian-phone" })
    public ResponseEntity<Integer> deleteGuardianPhoneNumber(@PathVariable("studentId") int studentId,
    @RequestParam String phoneNumber, Model model) {
        String guardianName = guardianDao.getNameByStudentId(studentId);
        GuardianPhoneNumber guardianPhoneNumber = new GuardianPhoneNumber(phoneNumber, guardianName, studentId);
        guardianPhoneNumberDao.delete(guardianPhoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
