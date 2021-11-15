package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.CourseDao;
import com.example.agrawal_classes.dao.StudentDao;
import com.example.agrawal_classes.dao.TestDao;
import com.example.agrawal_classes.model.Course;
import com.example.agrawal_classes.model.Test;
import com.example.agrawal_classes.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
public class TestController {
    @Autowired
    private TestDao testDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private StudentDao studentDao;

    @GetMapping({ "/admin/academics/tests", "/staff/academics/tests", "/teacher/academics/tests" })
    public String listTests(Model model) {
        model.addAttribute("title", "Academic Portal - Tests");
        model.addAttribute("message", "View all the tests");
        List<Test> tests = testDao.getAll();
        model.addAttribute("tests", tests);
        return "test/listTests";
    }

    @GetMapping("/student/academics/tests")
    public String listTestsStudent(Model model) {
        model.addAttribute("title", "Academic Portal - Tests");
        model.addAttribute("message", "View all the tests of your course");
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        List<Map<String, Object>> tests = testDao.getAllByStudentId(studentId);
        model.addAttribute("tests", tests);
        return "test/listTests";
    }

    @GetMapping({ "/admin/academics/tests/add", "/staff/academics/tests/add" })
    public String addTest(Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Tests");
        model.addAttribute("message", "Add Test");
        model.addAttribute("submessage1", "Add Test");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/tests/add");
        List<Course> courses = courseDao.getAll();
        model.addAttribute("courses", courses);
        Test test = new Test();
        model.addAttribute("test", test);
        return "test/addEditTest";
    }

    @PostMapping({ "/admin/academics/tests/add", "/staff/academics/tests/add" })
    public String addTest(@Valid @ModelAttribute("test") Test test, BindingResult bindingResult, Model model) {
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Tests");
            model.addAttribute("message", "Add Test");
            model.addAttribute("submessage1", "Add Test");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/tests/add");
            List<Course> courses = courseDao.getAll();
            model.addAttribute("courses", courses);
            return "test/addEditTest";
        }
        testDao.save(test);
        return "redirect:/" + role + "/academics/tests";
    }

    @GetMapping({ "/admin/academics/tests/{testId}", "/staff/academics/tests/{testId}" })
    public String viewTest(@PathVariable("testId") int testId, Model model) {
        model.addAttribute("title", "Academic Portal - Tests");
        model.addAttribute("message", "View Test");
        model.addAttribute("submessage1", "Test Details");
        Test test = testDao.get(testId);
        model.addAttribute("test", test);
        return "test/viewTest";
    }

    @GetMapping({ "/teacher/academics/tests/{testId}", "/student/academics/tests/{testId}" })
    public String viewTestRedirect(@PathVariable("testId") int testId, Model model) {
        String role = securityService.findLoggedInUserRole();
        return "redirect:/" + role + "/academics/tests/" + testId + "/results";
    }


    @GetMapping({ "/admin/academics/tests/{testId}/edit", "/staff/academics/tests/{testId}/edit" })
    public String editTest(@PathVariable("testId") int testId, Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Tests");
        model.addAttribute("message", "Edit Test");
        model.addAttribute("submessage1", "Edit Test");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/tests/" + testId + "/edit");
        model.addAttribute("edit", "true");
        List<Course> courses = courseDao.getAll();
        model.addAttribute("courses", courses);
        Test test = testDao.get(testId);
        model.addAttribute("test", test);
        return "test/addEditTest";
    }

    @PostMapping({ "/admin/academics/tests/{testId}/edit", "/staff/academics/tests/{testId}/edit" })
    public String editTest(@PathVariable("testId") int testId,
    @Valid @ModelAttribute("test") Test test, BindingResult bindingResult, Model model) {
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Tests");
            model.addAttribute("message", "Edit Test");
            model.addAttribute("submessage1", "Edit Test");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/tests/" + testId + "/edit");
            model.addAttribute("edit", "true");
            List<Course> courses = courseDao.getAll();
            model.addAttribute("courses", courses);
            return "test/addEditTest";
        }
        testDao.update(test);
        return "redirect:/" + role + "/academics/tests/" + testId;
    }

    @GetMapping({ "/admin/academics/tests/{testId}/delete", "/staff/academics/tests/{testId}/delete" })
    public ResponseEntity<Integer> deleteTest(@PathVariable("testId") int testId, Model model) {
        testDao.delete(testId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
