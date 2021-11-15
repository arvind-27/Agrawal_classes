package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.*;
import com.example.agrawal_classes.model.*;
//import com.example.agrawal_classes.services.PaymentService;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.validators.EnrollmentValidator;
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
import java.util.List;

@Transactional
@Controller
public class EnrollmentController {
    @Autowired
    private EnrollmentDao enrollmentDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private BatchDao batchDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private StaffDao staffDao;

//    @Autowired
//    private PaymentService paymentService;

    @Autowired
    private EnrollmentValidator enrollmentValidator;

    public void checkStaffAssignedBatch(String courseId, String batchId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("staff")) {
            int staffId = staffDao.getStaffIdByUserId(userId);
            List<Batch> batches = batchDao.getAllByStaffId(staffId);
            boolean forbidden = true;
            for (Batch batch : batches) {
                if (batch.getBatchId().equals(batchId) && batch.getCourse().getCourseId().equals(courseId)) {
                    forbidden = false;
                    break;
                }
            }
            if (forbidden)
                throw new AccessDeniedException("This batch is not assigned to you");
        }
    }

    public void checkTeacherAssignedBatch(String courseId, String batchId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("teacher")) {
            int teacherId = teacherDao.getTeacherIdByUserId(userId);
            List<Batch> batches = batchDao.getAllByTeacherId(teacherId);
            boolean forbidden = true;
            for (Batch batch : batches) {
                if (batch.getBatchId().equals(batchId) && batch.getCourse().getCourseId().equals(courseId)) {
                    forbidden = false;
                    break;
                }
            }
            if (forbidden)
                throw new AccessDeniedException("This batch is not assigned to you");
        }
    }

    @GetMapping({ "/admin/academics/enrollments", "/student/academics/enrollments" })
    public String listEnrollments(Model model) {
        model.addAttribute("title", "Academic Portal - Enrollments");
        model.addAttribute("message", "View all the enrollments");
        List<Enrollment> enrollments = enrollmentDao.getAll();

        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            enrollments = enrollmentDao.getAllByStudentId(studentId);
        }

        model.addAttribute("enrollments", enrollments);
        List<Course> courses = courseDao.getAll();
        model.addAttribute("courses", courses);
        model.addAttribute("fullenrollment", true);
        return "enrollment/listEnrollments";
    }

    @GetMapping("/admin/academics/courses/{courseId}/enrollments")
    public String listEnrollmentsByCourse(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("title", "Academic Portal - Enrollments");
        model.addAttribute("message", "View enrollments by Course");
        model.addAttribute("submessage1", "Course " + courseId);
        List<Enrollment> enrollments = enrollmentDao.getAllByCourseId(courseId);
        model.addAttribute("enrollments", enrollments);
        return "enrollment/listEnrollments";
    }

    @GetMapping({ "/admin/academics/courses/{courseId}/{batchId}/enrollments",
            "/staff/academics/courses/{courseId}/{batchId}/enrollments",
            "/teacher/academics/courses/{courseId}/{batchId}/enrollments" })
    public String listEnrollmentsByBatch(@PathVariable("courseId") String courseId,
    @PathVariable("batchId") String batchId, Model model) {
        checkStaffAssignedBatch(courseId, batchId);
        checkTeacherAssignedBatch(courseId, batchId);
        model.addAttribute("title", "Academic Portal - Enrollments");
        model.addAttribute("message", "View enrollments by Batch");
        model.addAttribute("submessage1", "Batch " + batchId + " of Course " + courseId);
        List<Enrollment> enrollments = enrollmentDao.getAllByBatch(courseId, batchId);
        model.addAttribute("enrollments", enrollments);
        return "enrollment/listEnrollments";
    }

    @GetMapping({ "/admin/academics/courses/{courseId}/{batchId}/enrollments/add",
            "/staff/academics/courses/{courseId}/{batchId}/enrollments/add",
            "/student/academics/courses/{courseId}/{batchId}/enrollments/add" })
    public String addEnrollment(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId, Model model) {
        checkStaffAssignedBatch(courseId, batchId);
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Enrollments");
        model.addAttribute("message", "Add Enrollment");
        model.addAttribute("submessage1", "Add Enrollment");
        model.addAttribute("submiturl", "/" + role + "/academics/courses/" + courseId + "/" + batchId + "/enrollments/add");
        List<Student> students = studentDao.getAll();
        model.addAttribute("students", students);
        int amount = batchDao.getFee(batchId, courseId);
        model.addAttribute("amount", amount);
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseId);
        enrollment.setBatchId(batchId);
        if (role.equals("student")) {
            int userId = securityService.findLoggedInUserId();
            int studentId = studentDao.getStudentIdByUserId(userId);
            enrollment.setStudentId(studentId);
            model.addAttribute("buttonmessage", "Pay and Finish");
            model.addAttribute("transactionMode", "Online");
        }
        else {
            model.addAttribute("buttonmessage", "Accept Payment and Finish");
            model.addAttribute("transactionMode", "Offline");
        }
        model.addAttribute("enrollment", enrollment);
        return "enrollment/addEditEnrollment";
    }

    @PostMapping({ "/admin/academics/courses/{courseId}/{batchId}/enrollments/add",
            "/staff/academics/courses/{courseId}/{batchId}/enrollments/add",
            "/student/academics/courses/{courseId}/{batchId}/enrollments/add" })
    public String addEnrollment(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId,
            @Valid @ModelAttribute("enrollment") Enrollment enrollment, BindingResult bindingResult, Model model) {
        System.out.println("You are here");
        checkStaffAssignedBatch(courseId, batchId);
        String role = securityService.findLoggedInUserRole();
        System.out.println("You are here 1");
        if (role.equals("student")) {
            int userId = securityService.findLoggedInUserId();
            int studentId = studentDao.getStudentIdByUserId(userId);
            enrollment.setStudentId(studentId);
        }
        System.out.println("You are here 2");
        enrollmentValidator.validate(enrollment, bindingResult);
        System.out.println("You are here 3");
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Enrollments");
            model.addAttribute("message", "Add Enrollment");
            model.addAttribute("submessage1", "Add Enrollment");
            int amount = batchDao.getFee(batchId, courseId);
            model.addAttribute("amount", amount);
            if (role.equals("student")) {
                model.addAttribute("buttonmessage", "Pay and Finish");
                model.addAttribute("transactionMode", "Online");
            } else {
                model.addAttribute("buttonmessage", "Accept Payment and Finish");
                model.addAttribute("transactionMode", "Offline");
            }
            model.addAttribute("submiturl", "/" + role + "/academics/courses/" + courseId + "/" + batchId + "/enrollments/add");
            List<Student> students = studentDao.getAll();
            model.addAttribute("students", students);
            return "enrollment/addEditEnrollment";
        }
        System.out.println("You are here 4");
        Transaction transaction = new Transaction();
        int amount = batchDao.getFee(batchId, courseId);
        transaction.setAmount(amount);
        transaction.setTransactionMode("Offline");
        System.out.println("You are here 5");
        if (role.equals("student")) {
            System.out.println("You are here 6");
            transaction.setIsSuccess(true);
            transaction = transactionDao.save(transaction);
            enrollment.setTransaction(transaction);
            enrollmentDao.save(enrollment);
            return "redirect:/" + role + "/academics/enrollments";
//            User user = securityService.findLoggedInUser();
//            transaction.setTransactionMode("Online");
//            transaction = transactionDao.save(transaction);
//            System.out.println("You are here 61");
//            return "redirect:" + paymentService.createPayment(user, transaction.getTransactionId(), amount, courseId, batchId);
        } else {
            System.out.println("You are here 7");
            transaction.setIsSuccess(true);
            transaction = transactionDao.save(transaction);
            enrollment.setTransaction(transaction);
            enrollmentDao.save(enrollment);
            return "redirect:/" + role + "/academics/enrollments";
        }
    }

//    @GetMapping("/student/transaction/{courseId}/{batchId}")
//    public String processTransaction(String transaction_id, @PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId) {
//        int transactionId = paymentService.getTransactionId(transaction_id);
//        String status = paymentService.getTransactionDetails(transactionId);
//        if (status.equals("completed")) {
//            transactionDao.setSuccess(transactionId);
//            Enrollment enrollment = new Enrollment();
//            enrollment.setCourseId(courseId);
//            enrollment.setBatchId(batchId);
//            int userId = securityService.findLoggedInUserId();
//            int studentId = studentDao.getStudentIdByUserId(userId);
//            enrollment.setStudentId(studentId);
//            Transaction transaction = transactionDao.get(transactionId);
//            enrollment.setTransaction(transaction);
//            enrollmentDao.save(enrollment);
//            return "redirect:/student/academics/enrollments";
//        }
//        return "redirect:/student/academics/enrollments?error";
//    }

    @GetMapping({ "/admin/academics/enrollments/{enrollmentId}",
            "/staff/academics/enrollments/{enrollmentId}",
            "/student/academics/enrollments/{enrollmentId}" })
    public String viewEnrollment(@PathVariable("enrollmentId") int enrollmentId, Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "View Enrollment");
        model.addAttribute("submessage1", "Enrollment Details");
        Enrollment enrollment = enrollmentDao.get(enrollmentId);
        model.addAttribute("enrollment", enrollment);
        checkStaffAssignedBatch(enrollment.getCourseId(), enrollment.getBatchId());
        return "enrollment/viewEnrollment";
    }

    @GetMapping({ "/admin/academics/enrollments/{enrollmentId}/edit", "/staff/academics/enrollments/{enrollmentId}/edit" })
    public String editEnrollment(@PathVariable("enrollmentId") int enrollmentId, Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Enrollments");
        model.addAttribute("message", "Edit Enrollment");
        model.addAttribute("submessage1", "Edit Enrollment");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/enrollments/" + enrollmentId + "/edit");
        model.addAttribute("edit", "true");
        Enrollment enrollment = enrollmentDao.get(enrollmentId);
        checkStaffAssignedBatch(enrollment.getCourseId(), enrollment.getBatchId());
        model.addAttribute("enrollment", enrollment);
        return "enrollment/addEditEnrollment";
    }

    @PostMapping({ "/admin/academics/enrollments/{enrollmentId}/edit", "/staff/academics/enrollments/{enrollmentId}/edit" })
    public String editEnrollment(@PathVariable("enrollmentId") int enrollmentId, @Valid @ModelAttribute("enrollment") Enrollment enrollment,
    BindingResult bindingResult, Model model) {
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Enrollments");
            model.addAttribute("message", "Edit Enrollment");
            model.addAttribute("submessage1", "Edit Enrollment");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/enrollments/" + enrollmentId + "/edit");
            model.addAttribute("edit", "true");
            return "enrollment/addEditEnrollment";
        }
        Enrollment oldEnrollment = enrollmentDao.get(enrollmentId);
        String courseId = oldEnrollment.getCourseId();
        String batchId = oldEnrollment.getBatchId();
        checkStaffAssignedBatch(courseId, batchId);
        enrollmentDao.update(enrollment);
        return "redirect:/" + role + "/academics/courses/" + courseId + "/" + batchId + "/enrollments";
    }

    @GetMapping("/admin/academics/enrollments/{enrollmentId}/delete")
    public ResponseEntity<Integer> deleteEnrollment(@PathVariable("enrollmentId") int enrollmentId, Model model) {
        enrollmentDao.delete(enrollmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
