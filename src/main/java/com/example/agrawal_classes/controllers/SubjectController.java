package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.StudentDao;
import com.example.agrawal_classes.dao.SubjectDao;
import com.example.agrawal_classes.dao.TeacherDao;
import com.example.agrawal_classes.model.Subject;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.validators.SubjectValidator;
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

@Transactional
@Controller
public class SubjectController {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SubjectValidator subjectValidator;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private StudentDao studentDao;

    @GetMapping({ "/admin/academics/subjects", "/staff/academics/subjects", "/subjects" })
    public String listSubjects(Model model) {
        model.addAttribute("title", "Academic Portal - Subjects");
        model.addAttribute("message", "View all the subjects");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);
        return "subject/listSubjects";
    }

    @GetMapping("/teacher/academics/subjects")
    public String listTeacherSubjects(Model model) {
        model.addAttribute("title", "Academic Portal - Subjects");
        model.addAttribute("message", "View all the subjects");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);
        int userId = securityService.findLoggedInUserId();
        int teacherId = teacherDao.getTeacherIdByUserId(userId);
        String teacherSubjectId = subjectDao.getSubjectCodeByTeacherId(teacherId);
        model.addAttribute("teacherSubjectId", teacherSubjectId);
        return "subject/listSubjects";
    }

    @GetMapping("/student/academics/subjects")
    public String listStudentSubjects(Model model) {
        model.addAttribute("title", "Academic Portal - Subjects");
        model.addAttribute("message", "View all the subjects");
        List<Subject> subjects = subjectDao.getAll();
        model.addAttribute("subjects", subjects);
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        List<String> studentSubjectsId = subjectDao.getSubjectCodeByStudentId(studentId);
        model.addAttribute("studentSubjectsId", studentSubjectsId);
        return "subject/listSubjects";
    }

    @GetMapping("/admin/academics/subjects/add")
    public String addSubject(Model model) {
        model.addAttribute("title", "Academic Portal - Subjects");
        model.addAttribute("message", "Add Subject");
        model.addAttribute("submessage1", "Add Subject");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/subjects/add");
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        return "subject/addEditSubject";
    }

    @PostMapping("/admin/academics/subjects/add")
    public String addSubject(@Valid @ModelAttribute("subject") Subject subject, BindingResult bindingResult, Model model) {
        subjectValidator.validate(subject, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Subjects");
            model.addAttribute("message", "Add Subject");
            model.addAttribute("submessage1", "Add Subject");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/subjects/add");
            return "subject/addEditSubject";
        }
        subjectDao.save(subject);
        return "redirect:/admin/academics/subjects";
    }

    @GetMapping("/admin/academics/subjects/{subjectId}")
    public String viewSubject(@PathVariable("subjectId") String subjectId, Model model) {
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "View Subject");
        model.addAttribute("submessage1", "Subject Details");
        Subject subject = subjectDao.get(subjectId);
        model.addAttribute("subject", subject);
        return "subject/viewSubject";
    }

    @GetMapping("/admin/academics/subjects/{subjectId}/edit")
    public String editSubject(@PathVariable("subjectId") String subjectId, Model model) {
        model.addAttribute("title", "Academic Portal - Subjects");
        model.addAttribute("message", "Edit Subject");
        model.addAttribute("submessage1", "Edit Subject");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/academics/subjects/" + subjectId + "/edit");
        model.addAttribute("edit", "true");
        Subject subject = subjectDao.get(subjectId);
        model.addAttribute("subject", subject);
        return "subject/addEditSubject";
    }

    @PostMapping("/admin/academics/subjects/{subjectId}/edit")
    public String editSubject(@PathVariable("subjectId") String subjectId, @Valid @ModelAttribute("subject") Subject subject,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Subjects");
            model.addAttribute("message", "Edit Subject");
            model.addAttribute("submessage1", "Edit Subject");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/academics/subjects/" + subjectId + "/edit");
            model.addAttribute("edit", "true");
            return "subject/addEditSubject";
        }
        subjectDao.update(subject);
        return "redirect:/admin/academics/subjects";
    }

    @GetMapping("/admin/academics/subjects/{subjectId}/delete")
    public ResponseEntity<Integer> deleteSubject(@PathVariable("subjectId") String subjectId, Model model) {
        subjectDao.delete(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
