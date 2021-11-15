package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.ComplaintDao;
import com.example.agrawal_classes.dao.StudentDao;
import com.example.agrawal_classes.model.Complaint;
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
public class ComplaintController {
    @Autowired
    private ComplaintDao complaintDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private SecurityService securityService;

    public void checkComplaintBelongingToStudent(int complaintId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            Complaint complaint = complaintDao.get(complaintId);
            if (complaint.getStudentId() != studentId)
                throw new AccessDeniedException("This complaint is not raised by you");
        }
    }

    @GetMapping({ "/admin/complaints", "/student/complaints" })
    public String listComplaints(Model model) {
        model.addAttribute("title", "Complaint Portal");
        model.addAttribute("message", "View all the complaints");
        List<Complaint> complaints;
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            complaints = complaintDao.getAllByStudentId(studentId);
        }
        else {
            complaints = complaintDao.getAll();
        }
        model.addAttribute("complaints", complaints);
        return "complaint/listComplaints";
    }

    @GetMapping("/student/complaints/add")
    public String addComplaint(Model model) {
        model.addAttribute("title", "Complaint Portal");
        model.addAttribute("message", "Add Complaint");
        model.addAttribute("submessage1", "Add Complaint");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/student/complaints/add");
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        model.addAttribute("studentId", studentId);
        Complaint complaint = new Complaint();
        model.addAttribute("complaint", complaint);
        return "complaint/addEditComplaint";
    }

    @PostMapping("/student/complaints/add")
    public String addComplaint(@Valid @ModelAttribute("complaint") Complaint complaint, BindingResult bindingResult,
            Model model) {
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Complaint Portal");
            model.addAttribute("message", "Add Complaint");
            model.addAttribute("submessage1", "Add Complaint");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/student/complaints/add");
            model.addAttribute("studentId", studentId);
            return "complaint/addEditComplaint";
        }
        complaint.setStudentId(studentId);
        complaintDao.save(complaint);
        return "redirect:/student/complaints";
    }

    @GetMapping({ "/admin/complaints/{complaintId}", "/student/complaints/{complaintId}" })
    public String viewComplaint(@PathVariable("complaintId") int complaintId, Model model) {
        checkComplaintBelongingToStudent(complaintId);
        model.addAttribute("title", "Complaint Portal");
        model.addAttribute("message", "View Complaint");
        model.addAttribute("submessage1", "Complaint Details");
        Complaint complaint = complaintDao.get(complaintId);
        model.addAttribute("complaint", complaint);
        return "complaint/viewComplaint";
    }

    @GetMapping("/admin/complaints/{complaintId}/edit")
    public String editComplaintAdmin(@PathVariable("complaintId") int complaintId, Model model) {
        model.addAttribute("title", "Complaint Portal");
        model.addAttribute("message", "Edit Complaint");
        model.addAttribute("submessage1", "Edit Complaint");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/admin/complaints/" + complaintId + "/edit");
        model.addAttribute("edit", "true");
        Complaint complaint = complaintDao.get(complaintId);
        model.addAttribute("complaint", complaint);
        return "complaint/editComplaintAdmin";
    }

    @PostMapping("/admin/complaints/{complaintId}/edit")
    public String editComplaintAdmin(@PathVariable("complaintId") int complaintId, @Valid @ModelAttribute("complaint") Complaint complaint,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Complaint Portal");
            model.addAttribute("message", "Edit Complaint");
            model.addAttribute("submessage1", "Edit Complaint");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/admin/complaints/" + complaintId + "/edit");
            model.addAttribute("edit", "true");
            return "complaint/editComplaintAdmin";
        }
        complaintDao.updateAdmin(complaint);
        return "redirect:/admin/complaints";
    }

    @GetMapping("/student/complaints/{complaintId}/edit")
    public String editComplaintStudent(@PathVariable("complaintId") int complaintId, Model model) {
        checkComplaintBelongingToStudent(complaintId);
        model.addAttribute("title", "Complaint Portal");
        model.addAttribute("message", "Edit Complaint");
        model.addAttribute("submessage1", "Edit Complaint");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/student/complaints/" + complaintId + "/edit");
        model.addAttribute("edit", "true");
        Complaint complaint = complaintDao.get(complaintId);
        model.addAttribute("complaint", complaint);
        if (complaint.isIsResolved())
            return "redirect:/student/complaints/" + complaintId;
        return "complaint/addEditComplaint";
    }

    @PostMapping("/student/complaints/{complaintId}/edit")
    public String editComplaintStudent(@PathVariable("complaintId") int complaintId,
            @ModelAttribute("complaint") Complaint complaint, BindingResult bindingResult, Model model) {
        checkComplaintBelongingToStudent(complaintId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Complaint Portal");
            model.addAttribute("message", "Edit Complaint");
            model.addAttribute("submessage1", "Edit Complaint");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/student/complaints/" + complaintId + "/edit");
            model.addAttribute("edit", "true");
            return "complaint/addEditComplaint";
        }
        if (complaint.isIsResolved())
            return "redirect:/student/complaints/" + complaintId;
        complaintDao.updateStudent(complaint);
        return "redirect:/student/complaints";
    }

    @PostMapping("/admin/complaints/{complaintId}/resolve")
    public ResponseEntity<Integer> resolveComplaint(@PathVariable("complaintId") int complaintId,
            @RequestParam String response, Model model) {
        complaintDao.resolve(complaintId, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({ "/admin/complaints/{complaintId}/delete", "/student/complaints/{complaintId}/delete" })
    public ResponseEntity<Integer> deleteComplaint(@PathVariable("complaintId") int complaintId, Model model) {
        checkComplaintBelongingToStudent(complaintId);
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            Complaint complaint = complaintDao.get(complaintId);
            if (complaint.isIsResolved())
                throw new AccessDeniedException("You can't delete this complaint");
        }
        complaintDao.delete(complaintId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
