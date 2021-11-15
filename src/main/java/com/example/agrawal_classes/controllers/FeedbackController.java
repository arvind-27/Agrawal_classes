package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.EmployeeDao;
import com.example.agrawal_classes.dao.FeedbackDao;
import com.example.agrawal_classes.dao.StudentDao;
import com.example.agrawal_classes.dao.TeacherDao;
import com.example.agrawal_classes.model.Feedback;
import com.example.agrawal_classes.model.Teacher;
import com.example.agrawal_classes.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Transactional
@Controller
public class FeedbackController {
    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private SecurityService securityService;

    public void checkFeedbackBelongingToStudent(int feedbackId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            Feedback feedback = feedbackDao.get(feedbackId);
            if (feedback.getStudentId() != studentId)
                throw new AccessDeniedException("This feedback is not given by you");
        }
    }

    public void checkFeedbackBelongingToTeacher(int feedbackId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("teacher")) {
            int employeeId = employeeDao.getEmployeeIdByUserId(userId);
            Feedback feedback = feedbackDao.get(feedbackId);
            if (feedback.getEmployeeId() != employeeId)
                throw new AccessDeniedException("This feedback is not given to you");
        }
    }

    @GetMapping({ "/admin/feedbacks", "/student/feedbacks", "/teacher/feedbacks"  })
    public String listFeedbacks(Model model) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Feedback Portal");
        model.addAttribute("message", "View all the student-teacher feedback and communication");
        List<Feedback> feedbacks;
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            feedbacks = feedbackDao.getAllByStudentId(studentId);
        } else if (role.equals("teacher")) {
            int employeeId = employeeDao.getEmployeeIdByUserId(userId);
            feedbacks = feedbackDao.getAllByEmployeeId(employeeId);
        } else {
            feedbacks = feedbackDao.getAll();
        }
        model.addAttribute("feedbacks", feedbacks);
        return "feedback/listFeedbacks";
    }

    @GetMapping("/student/feedbacks/add")
    public String addFeedback(Model model) {
        model.addAttribute("title", "Feedback Portal");
        model.addAttribute("message", "Add Feedback");
        model.addAttribute("submessage1", "Add Feedback");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/student/feedbacks/add");
        List<Teacher> teachers = teacherDao.getAll();
        model.addAttribute("teachers", teachers);
        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);
        return "feedback/addFeedback";
    }

    @PostMapping("/student/feedbacks/add")
    public String addFeedback(@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Feedback Portal");
            model.addAttribute("message", "Add Feedback");
            model.addAttribute("submessage1", "Add Feedback");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/student/feedbacks/add");
            List<Teacher> teachers = teacherDao.getAll();
            model.addAttribute("teachers", teachers);
            return "feedback/addFeedback";
        }
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        feedback.setStudentId(studentId);
        feedbackDao.save(feedback);
        return "redirect:/student/feedbacks";
    }

    @PostMapping("/teacher/feedbacks/{feedbackId}/respond")
    public ResponseEntity<Integer> respondFeedback(@PathVariable("feedbackId") int feedbackId, @RequestParam String response, Model model) {
        checkFeedbackBelongingToTeacher(feedbackId);
        Feedback feedback = feedbackDao.get(feedbackId);
        if (feedback.getResponse() != null)
            throw new AccessDeniedException("You have already responded");
        feedbackDao.respond(feedbackId, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({ "/admin/feedbacks/{feedbackId}/delete", "/student/feedbacks/{feedbackId}/delete" })
    public ResponseEntity<Integer> deleteFeedback(@PathVariable("feedbackId") int feedbackId, Model model) {
        checkFeedbackBelongingToStudent(feedbackId);
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            Feedback feedback = feedbackDao.get(feedbackId);
            if (feedback.getResponse() != null)
                throw new AccessDeniedException("You can't delete this feedback");
        }
        feedbackDao.delete(feedbackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
