package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.StudentDao;
import com.example.agrawal_classes.dao.StudyMaterialDao;
import com.example.agrawal_classes.dao.SubjectDao;
import com.example.agrawal_classes.dao.TeacherDao;
import com.example.agrawal_classes.model.StudyMaterial;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.services.StorageService;
import com.example.agrawal_classes.validators.StudyMaterialValidator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class StudyMaterialController {
    @Autowired
    private StudyMaterialDao studyMaterialDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private StudyMaterialValidator studyMaterialValidator;

    public void checkStudentEnrolledSubject(String subjectId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            List<String> subjects = subjectDao.getSubjectCodeByStudentId(studentId);
            if (!subjects.contains(subjectId))
                throw new AccessDeniedException("You have not enrolled for this subject");
        }
    }

    public void checkTeacherOfSubject(String subjectId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("teacher")) {
            int teacherId = teacherDao.getTeacherIdByUserId(userId);
            String subject = subjectDao.getSubjectCodeByTeacherId(teacherId);
            if (!subject.equals(subjectId))
                throw new AccessDeniedException("You are not a teacher of this subject");
        }
    }

    @GetMapping({ "/admin/academics/subjects/{subjectId}/materials",
            "/student/academics/subjects/{subjectId}/materials",
            "/teacher/academics/subjects/{subjectId}/materials" })
    public String listStudyMaterials(@PathVariable("subjectId") String subjectId, Model model) {
        checkStudentEnrolledSubject(subjectId);
        checkTeacherOfSubject(subjectId);
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Study Materials");
        model.addAttribute("message", "View all the study materials");
        List<StudyMaterial> materials = studyMaterialDao.getAllBySubjectId(subjectId);
        model.addAttribute("materials", materials);
        List<String> urls = new ArrayList<>();
        for (StudyMaterial material : materials) {
            urls.add("/" + role + "/academics/subjects/" + subjectId + "/materials/" + UriUtils.encodePath(material.getMaterialId(), "UTF-8") + "/view");
        }
        model.addAttribute("urls", urls);
        return "studymaterial/listMaterials";
    }

    @GetMapping({ "/admin/academics/subjects/{subjectId}/materials/add",
            "/teacher/academics/subjects/{subjectId}/materials/add" })
    public String addStudyMaterial(@PathVariable("subjectId") String subjectId, Model model) {
        String role = securityService.findLoggedInUserRole();
        checkTeacherOfSubject(subjectId);
        model.addAttribute("title", "Academic Portal - Study Materials");
        model.addAttribute("message", "Add Study Material");
        model.addAttribute("submessage1", "Add Study Material");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/subjects/" + subjectId + "/materials/add");
        StudyMaterial material= new StudyMaterial();
        model.addAttribute("material", material);
        return "studymaterial/addEditMaterial";
    }

    @PostMapping({ "/admin/academics/subjects/{subjectId}/materials/add",
            "/teacher/academics/subjects/{subjectId}/materials/add" })
    public String addStudyMaterial(@PathVariable("subjectId") String subjectId,
            @Valid @ModelAttribute("material") StudyMaterial material, BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile file) {
        studyMaterialValidator.validate(material, bindingResult);
        String role = securityService.findLoggedInUserRole();
        checkTeacherOfSubject(subjectId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Study Materials");
            model.addAttribute("message", "Add Study Material");
            model.addAttribute("submessage1", "Add Study Material");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/subjects/" + subjectId + "/materials/add");
            return "studymaterial/addEditMaterial";
        }
        material.setFilename(file.getOriginalFilename());
        studyMaterialDao.save(material);
        storageService.save(subjectId, file);
        return "redirect:/" + role + "/academics/subjects/" + subjectId + "/materials";
    }

    @GetMapping({ "/admin/academics/subjects/{subjectId}/materials/{materialId}",
            "/teacher/academics/subjects/{subjectId}/materials/{materialId}" })
    public String viewStudyMaterial(@PathVariable("subjectId") String subjectId,
            @PathVariable("materialId") String materialId, Model model) {
        checkTeacherOfSubject(subjectId);
        model.addAttribute("title", "Academic Portal - Courses");
        model.addAttribute("message", "View Study Material");
        model.addAttribute("submessage1", "Study Material Details");
        StudyMaterial material= studyMaterialDao.get(subjectId, materialId);
        model.addAttribute("material", material);
        String role = securityService.findLoggedInUserRole();
        String url = "/" + role + "/academics/subjects/" + subjectId + "/materials/" + UriUtils.encodePath(material.getMaterialId(), "UTF-8") + "/view";
        model.addAttribute("url", url);
        return "studymaterial/viewMaterial";
    }

    @GetMapping({"/admin/academics/subjects/{subjectId}/materials/{materialId}/view",
            "/teacher/academics/subjects/{subjectId}/materials/{materialId}/view",
            "/student/academics/subjects/{subjectId}/materials/{materialId}/view"})
    public void viewStudyMaterialFile(HttpServletResponse response, @PathVariable("subjectId") String subjectId,
            @PathVariable("materialId") String materialId) {
        checkStudentEnrolledSubject(subjectId);
        checkTeacherOfSubject(subjectId);
        try {
            StudyMaterial material= studyMaterialDao.get(subjectId, materialId);
            Path path = storageService.getFilePath(subjectId, material.getFilename());
            String location = storageService.getFileLocation(subjectId, material.getFilename());
            System.out.println(location);
            InputStream is = new FileInputStream(location);
            String x = Files.probeContentType(path);
            System.out.println(x);
            response.setContentType(x);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @GetMapping({ "/admin/academics/subjects/{subjectId}/materials/{materialId}/edit",
            "/teacher/academics/subjects/{subjectId}/materials/{materialId}/edit" })
    public String editStudyMaterial(@PathVariable("subjectId") String subjectId,
    @PathVariable("materialId") String materialId, Model model) {
        String role = securityService.findLoggedInUserRole();
        checkTeacherOfSubject(subjectId);
        model.addAttribute("title", "Academic Portal - Study Materials");
        model.addAttribute("message", "Edit Study Material");
        model.addAttribute("submessage1", "Edit Study Material");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/subjects/" + subjectId + "/materials/" + materialId + "/edit");
        model.addAttribute("edit", "true");
        StudyMaterial material = studyMaterialDao.get(subjectId, materialId);
        model.addAttribute("material", material);
        String url = "/" + role + "/academics/subjects/" + subjectId + "/materials/"
                + UriUtils.encodePath(material.getMaterialId(), "UTF-8") + "/view";
        model.addAttribute("url", url);
        return "studymaterial/addEditMaterial";
    }

    @PostMapping({ "/admin/academics/subjects/{subjectId}/materials/{materialId}/edit",
            "/teacher/academics/subjects/{subjectId}/materials/{materialId}/edit" })
    public String editStudyMaterial(@PathVariable("subjectId") String subjectId,
            @PathVariable("materialId") String materialId, @Valid @ModelAttribute("material") StudyMaterial material,
            BindingResult bindingResult, Model model) {
        String role = securityService.findLoggedInUserRole();
        checkTeacherOfSubject(subjectId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Study Materials");
            model.addAttribute("message", "Edit Study Material");
            model.addAttribute("submessage1", "Edit Study Material");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/subjects/" + subjectId + "/materials/" + materialId + "/edit");
            model.addAttribute("edit", "true");
            String url = "/" + role + "/academics/subjects/" + subjectId + "/materials/"
                    + UriUtils.encodePath(material.getMaterialId(), "UTF-8") + "/view";
            model.addAttribute("url", url);
            return "studymaterial/addEditMaterial";
        }
        studyMaterialDao.update(material);
        return "redirect:/" + role + "/academics/subjects/" + subjectId + "/materials";
    }

    @GetMapping({ "/admin/academics/subjects/{subjectId}/materials/{materialId}/delete",
            "/teacher/academics/subjects/{subjectId}/materials/{materialId}/delete" })
    public ResponseEntity<Integer> deleteStudyMaterial(@PathVariable("subjectId") String subjectId,
            @PathVariable("materialId") String materialId, Model model) {
        checkTeacherOfSubject(subjectId);
        String filename = studyMaterialDao.getFilename(subjectId, materialId);
        storageService.delete(subjectId, filename);
        studyMaterialDao.delete(subjectId, materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
