package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.*;
import com.example.agrawal_classes.model.*;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.validators.BatchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class BatchController {
    @Autowired
    private BatchDao batchDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private StaffBatchDao staffBatchDao;

    @Autowired
    private TeacherBatchDao teacherBatchDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BatchValidator batchValidator;

    @Autowired
    private SubjectDao subjectDao;

    @GetMapping({ "/admin/academics/batches", "/student/academics/batches", "/staff/academics/batches",
            "/teacher/academics/batches" })
    public String listBatches(Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "View all the batches");
        List<Batch> batches = batchDao.getAll();
        model.addAttribute("batches", batches);
        return "batch/listBatches";
    }

    @GetMapping({ "/staff/academics/batches-assigned", "/teacher/academics/batches-assigned" })
    public String listBatchesAssigned(Model model) {
        model.addAttribute("title", "Academic Portal - Batches Assigned");
        model.addAttribute("message", "View all the batches assigned to you");
        List<Batch> batches;
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("teacher")) {
            int teacherId = teacherDao.getTeacherIdByUserId(userId);
            batches = batchDao.getAllByTeacherId(teacherId);
        }
        else {
            int staffId = staffDao.getStaffIdByUserId(userId);
            batches = batchDao.getAllByStaffId(staffId);
        }
        model.addAttribute("batches", batches);
        return "batch/listBatchesAssigned";
    }

    @GetMapping("/admin/academics/batches/add")
    public String addBatch1(Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "Add Batch");
        model.addAttribute("submessage1", "Add Batch");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/batches/add");
        List<Course> courses = courseDao.getAll();
        model.addAttribute("courses", courses);
        Batch batch = new Batch();
        model.addAttribute("batch", batch);
        return "batch/addEditBatch";
    }

    @GetMapping("/admin/academics/courses/{courseId}/add-batch")
    public String addBatch2(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "Add Batch");
        model.addAttribute("submessage1", "Add Batch");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/add-batch");
        Course course = courseDao.get(courseId);
        model.addAttribute("course", course);
        Batch batch = new Batch();
        model.addAttribute("batch", batch);
        return "batch/addEditBatch";
    }

    @PostMapping("/admin/academics/batches/add")
    public String addBatch1(@Valid @ModelAttribute("batch") Batch batch, BindingResult bindingResult, Model model) {
        batchValidator.validate(batch, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Batches");
            model.addAttribute("message", "Add Batch");
            model.addAttribute("submessage1", "Add Batch");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/batches/add");
            List<Course> courses = courseDao.getAll();
            model.addAttribute("courses", courses);
            return "batch/addEditBatch";
        }
        batchDao.save(batch);
        return "redirect:/admin/academics/batches";
    }

    @PostMapping("/admin/academics/courses/{courseId}/add-batch")
    public String addBatch2(@PathVariable("courseId") String courseId,
            @Valid @ModelAttribute("batch") Batch batch, BindingResult bindingResult, Model model) {
        batchValidator.validate(batch, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Batches");
            model.addAttribute("message", "Add Batch");
            model.addAttribute("submessage1", "Add Batch");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/add-batch");
            Course course = courseDao.get(courseId);
            model.addAttribute("course", course);
            return "batch/addEditBatch";
        }
        Course course = batch.getCourse();
        course.setCourseId(courseId);
        batch.setCourse(course);
        batchDao.save(batch);
        return "redirect:/admin/academics/courses/" + courseId;
    }

    @GetMapping({ "/admin/academics/courses/{courseId}/{batchId}", "/student/academics/courses/{courseId}/{batchId}",
            "/staff/academics/courses/{courseId}/{batchId}", "/teacher/academics/courses/{courseId}/{batchId}" })
    public String viewBatch(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId, Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "View Batch");
        model.addAttribute("submessage1", "Batch Details");
        Batch batch = batchDao.get(batchId, courseId);
        List<Staff> staffs = staffDao.getAllByBatch(batchId, courseId);
        List<Teacher> teachers = teacherDao.getAllByBatch(batchId, courseId);
        model.addAttribute("batch", batch);
        model.addAttribute("staffs", staffs);
        model.addAttribute("teachers", teachers);
        return "batch/viewBatch";
    }

    @GetMapping("/admin/academics/courses/{courseId}/{batchId}/edit")
    public String editBatch(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId,
            Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "Edit Batch");
        model.addAttribute("submessage1", "Edit Batch");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/" + batchId + "/edit");
        model.addAttribute("edit", "true");
        List<Course> courses = courseDao.getAll();
        model.addAttribute("courses", courses);
        Batch batch = batchDao.get(batchId, courseId);
        model.addAttribute("batch", batch);
        return "batch/addEditBatch";
    }

    @PostMapping("/admin/academics/courses/{courseId}/{batchId}/edit")
    public String editBatch(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId,
            @Valid @ModelAttribute("batch") Batch batch, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Batches");
            model.addAttribute("message", "Edit Batch");
            model.addAttribute("submessage1", "Edit Batch");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/" + batchId + "/edit");
            model.addAttribute("edit", "true");
            List<Course> courses = courseDao.getAll();
            model.addAttribute("courses", courses);
            return "batch/addEditBatch";
        }
        Course course = batch.getCourse();
        course.setCourseId(courseId);
        batch.setCourse(course);

        batchDao.update(batch);
        return "redirect:/admin/academics/courses/" + courseId + "/" + batchId;
    }

    @GetMapping("/admin/academics/courses/{courseId}/{batchId}/delete")
    public ResponseEntity<Integer> deleteBatch(@PathVariable("courseId") String courseId,
            @PathVariable("batchId") String batchId, Model model) {
        batchDao.delete(batchId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/academics/courses/{courseId}/{batchId}/add-staff")
    public String addStaffToBatch(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId,
            Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "Add Staff to Batch");
        model.addAttribute("submessage1", "Add Staff - " + batchId + " (" + courseId + ")");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/" + batchId + "/add-staff");
        List<Staff> staffsPresent = staffDao.getStaffsInBatch(batchId, courseId);
        List<Staff> staffsNotPresent = staffDao.getStaffsNotInBatch(batchId, courseId);
        model.addAttribute("staffsPresent", staffsPresent);
        model.addAttribute("staffsNotPresent", staffsNotPresent);
        return "staff/addStaffBatch";
    }

    @PostMapping("/admin/academics/courses/{courseId}/{batchId}/add-staff")
    public ResponseEntity<String> addStaffToBatch(@PathVariable("courseId") String courseId,
            @PathVariable("batchId") String batchId, @RequestParam String staffId, Model model) {
        staffBatchDao.save(staffId, batchId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/academics/courses/{courseId}/{batchId}/delete-staff")
    public ResponseEntity<Integer> deleteStaffFromBatch(@PathVariable("courseId") String courseId,
            @PathVariable("batchId") String batchId, @RequestParam String staffId, Model model) {
        staffBatchDao.delete(staffId, batchId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/academics/courses/{courseId}/{batchId}/add-teacher")
    public String addTeacherToBatch(@PathVariable("courseId") String courseId, @PathVariable("batchId") String batchId,
            Model model) {
        model.addAttribute("title", "Academic Portal - Batches");
        model.addAttribute("message", "Add Teacher to Batch");
        model.addAttribute("submessage1", "Add Teacher - " + batchId + " (" + courseId + ")");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/" + batchId + "/add-teacher");
        List<Teacher> teachersPresent = teacherDao.getTeachersInBatch(batchId, courseId);
        List<Teacher> teachersNotPresentAll = teacherDao.getTeachersNotInBatch(batchId, courseId);

        List<Teacher> teachersNotPresent = new ArrayList<>();
        List<Subject> subjects = subjectDao.getSubjectsInCourse(courseId);
        for (Teacher teacher : teachersNotPresentAll) {
            boolean valid = false;
            for (Subject subject : subjects) {
                if (teacher.getSubject().getSubjectId().equals(subject.getSubjectId())) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                teachersNotPresent.add(teacher);
            }
        }
        model.addAttribute("teachersPresent", teachersPresent);
        model.addAttribute("teachersNotPresent", teachersNotPresent);
        return "teacher/addTeacherBatch";
    }

    @PostMapping("/admin/academics/courses/{courseId}/{batchId}/add-teacher")
    public ResponseEntity<String> addTeacherToBatch(@PathVariable("courseId") String courseId,
            @PathVariable("batchId") String batchId, @RequestParam String teacherId, Model model) {
        teacherBatchDao.save(teacherId, batchId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/academics/courses/{courseId}/{batchId}/delete-teacher")
    public ResponseEntity<Integer> deleteTeacherFromBatch(@PathVariable("courseId") String courseId,
            @PathVariable("batchId") String batchId, @RequestParam String teacherId, Model model) {
        teacherBatchDao.delete(teacherId, batchId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
