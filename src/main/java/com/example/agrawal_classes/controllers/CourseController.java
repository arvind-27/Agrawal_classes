package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.BatchDao;
import com.example.agrawal_classes.dao.CourseDao;
import com.example.agrawal_classes.dao.CourseSubjectDao;
import com.example.agrawal_classes.dao.SubjectDao;
import com.example.agrawal_classes.model.Batch;
import com.example.agrawal_classes.model.Course;
import com.example.agrawal_classes.model.CourseSubjectDetails;
import com.example.agrawal_classes.model.Subject;
import com.example.agrawal_classes.validators.CourseValidator;
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
import java.util.Map;

@Transactional
@Controller
public class CourseController {
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private BatchDao batchDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private CourseSubjectDao courseSubjectDao;

    @Autowired
    private CourseValidator courseValidator;

    @GetMapping({ "/admin/academics/courses", "/student/academics/courses", "/staff/academics/courses",
            "/teacher/academics/courses", "/courses" })
    public String listCourses(Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "View all the courses");
        List<Map<String, Object>> courses = courseDao.getAllList();
        List<CourseSubjectDetails> courseSubjects = courseSubjectDao.getAll();
        for (Map<String, Object> course : courses) {
            List<Subject> subjects = new ArrayList<>();
            for (CourseSubjectDetails courseSubject : courseSubjects) {
                if (courseSubject.getCourseId().equals(course.get("courseId"))) {
                    subjects.add(courseSubject.getSubject());
                }
            }
            course.put("subjects", subjects);
        }
        model.addAttribute("courses", courses);
        return "course/listCourses";
    }

    @GetMapping("/admin/academics/courses/add")
    public String addCourse(Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "Add Course");
        model.addAttribute("submessage1", "Add Course");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/add");
        Course course = new Course();
        model.addAttribute("course", course);
        return "course/addEditCourse";
    }

    @PostMapping("/admin/academics/courses/add")
    public String addCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult, Model model) {
        courseValidator.validate(course, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Courses");
            model.addAttribute("message", "Add Course");
            model.addAttribute("submessage1", "Add Course");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/courses/add");
            return "course/addEditCourse";
        }
        courseDao.save(course);
        return "redirect:/admin/academics/courses";
    }

    @GetMapping({ "/admin/academics/courses/{courseId}", "/student/academics/courses/{courseId}",
            "/staff/academics/courses/{courseId}", "/teacher/academics/courses/{courseId}" })
    public String viewCourse(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "View Course");
        model.addAttribute("submessage1", "Course Details");
        Course course = courseDao.get(courseId);
        List<Subject> subjects = subjectDao.getSubjectsInCourse(courseId);
        List<Batch> batches = batchDao.getAllByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("subjects", subjects);
        model.addAttribute("batches", batches);
        return "course/viewCourse";
    }

    @GetMapping("/admin/academics/courses/{courseId}/edit")
    public String editCourse(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "Edit Course");
        model.addAttribute("submessage1", "Edit Course");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/edit");
        model.addAttribute("edit", "true");
        Course course = courseDao.get(courseId);
        model.addAttribute("course", course);
        return "course/addEditCourse";
    }

    @PostMapping("/admin/academics/courses/{courseId}/edit")
    public String editCourse(@PathVariable("courseId") String courseId, @Valid @ModelAttribute("course") Course course,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Courses");
            model.addAttribute("message", "Edit Course");
            model.addAttribute("submessage1", "Edit Course");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/edit");
            model.addAttribute("edit", "true");
            return "course/addEditCourse";
        }
        courseDao.update(course);
        return "redirect:/admin/academics/courses";
    }

    @GetMapping("/admin/academics/courses/{courseId}/delete")
    public ResponseEntity<Integer> deleteCourse(@PathVariable("courseId") String courseId, Model model) {
        courseDao.delete(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/academics/courses/{courseId}/add-subject")
    public String addSubjectToCourse(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "Add Subject");
        model.addAttribute("submessage1", "Add Subject - " + courseId);
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/courses/" + courseId + "/add-subject");
        List<Subject> subjectsPresent = subjectDao.getSubjectsInCourse(courseId);
        List<Subject> subjectsNotPresent = subjectDao.getSubjectsNotInCourse(courseId);
        model.addAttribute("subjectsPresent", subjectsPresent);
        model.addAttribute("subjectsNotPresent", subjectsNotPresent);
        return "course/addCourseSubject";
    }

    @PostMapping("/admin/academics/courses/{courseId}/add-subject")
    public ResponseEntity<String> addSubjectToCourse(@PathVariable("courseId") String courseId,
            @RequestParam String subjectId, Model model) {
        courseSubjectDao.save(courseId, subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/academics/courses/{courseId}/delete-subject")
    public ResponseEntity<Integer> deleteSubjectFromCourse(@PathVariable("courseId") String courseId,
            @RequestParam String subjectId, Model model) {
        courseSubjectDao.delete(courseId, subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/admin/academics/courses/{courseId}/get-all-batches", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Batch> getAllBatches(@PathVariable("courseId") String courseId) {
        List<Batch> batches = batchDao.getAllByCourseId(courseId);
        return batches;
    }

    @PostMapping(path = "/student/academics/courses/{courseId}/get-all-batches", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Batch> getAllBatchesStudent(@PathVariable("courseId") String courseId) {
        List<Batch> batches = batchDao.getAllByCourseId(courseId);
        return batches;
    }

}
