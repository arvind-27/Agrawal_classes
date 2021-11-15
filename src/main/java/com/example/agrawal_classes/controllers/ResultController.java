package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.ResultDao;
import com.example.agrawal_classes.dao.StudentDao;
import com.example.agrawal_classes.dao.TestDao;
import com.example.agrawal_classes.model.Result;
import com.example.agrawal_classes.model.Student;
import com.example.agrawal_classes.model.Test;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.validators.ResultValidator;
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
import java.util.*;

@Transactional
@Controller
public class ResultController {
    @Autowired
    private ResultDao resultDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ResultValidator resultValidator;

    @Autowired
    private TestDao testDao;

    public Map<Integer, Integer> getMarksToRank(List<Result> results) {
        Set<Integer> marks = new LinkedHashSet<>();
        for (Result result : results) {
            marks.add(result.getMarksScored());
        }
        ArrayList<Integer> marksList = new ArrayList<>();
        marksList.addAll(marks);
        Collections.sort(marksList, Collections.reverseOrder());
        Map<Integer, Integer> marksToRank = new HashMap<>();
        int count = 1;
        for (Integer mark : marksList) {
            marksToRank.put(mark, count++);
        }
        return marksToRank;
    }

    public boolean checkStudentAppearedInTest(int testId) {
        int userId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (role.equals("student")) {
            int studentId = studentDao.getStudentIdByUserId(userId);
            int isAppearedInTest = resultDao.isStudentAppearedInTest(testId, studentId);
            if (isAppearedInTest == 0)
                throw new AccessDeniedException("You have not appeared in this test");
        }
        return true;
    }

    @GetMapping({ "/admin/academics/tests/{testId}/results", "/student/academics/tests/{testId}/results",
            "/teacher/academics/tests/{testId}/results", "/staff/academics/tests/{testId}/results" })
    public String listResults(@PathVariable("testId") int testId, Model model) {
        checkStudentAppearedInTest(testId);
        model.addAttribute("title", "Academic Portal - Results");
        model.addAttribute("message", "View all the results");
        List<Result> results = resultDao.getAllByTestId(testId);
        model.addAttribute("results", results);
        Map<Integer, Integer> marksToRank = getMarksToRank(results);
        model.addAttribute("marksToRank", marksToRank);
        int maximumMarks = testDao.getMaximumMarks(testId);
        model.addAttribute("maximumMarks", maximumMarks);
        return "result/listResults";
    }

    @GetMapping({ "/admin/academics/tests/{testId}/results/add", "/staff/academics/tests/{testId}/results/add" })
    public String addResult(@PathVariable("testId") int testId, Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Results");
        model.addAttribute("message", "Add Result");
        model.addAttribute("submessage1", "Add Result");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/tests/" + testId + "/results/add");
        Test test = testDao.get(testId);
        List<Student> students = studentDao.getAllByCourseId(test.getCourse().getCourseId());
        model.addAttribute("students", students);
        Result result = new Result();
        model.addAttribute("result", result);
        int maximumMarks = testDao.getMaximumMarks(testId);
        model.addAttribute("maximumMarks", maximumMarks);
        return "result/addEditResult";
    }

    @PostMapping({ "/admin/academics/tests/{testId}/results/add", "/staff/academics/tests/{testId}/results/add" })
    public String addResult(@PathVariable("testId") int testId, @Valid @ModelAttribute("result") Result result,
            BindingResult bindingResult, Model model) {
        Test test = testDao.get(testId);
        if (result.getMarksScored() > test.getMaximumMarks()) {
            bindingResult.rejectValue("marksScored", "Invalid.result.marksScored");
        }
        resultValidator.validate(result, bindingResult);
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Results");
            model.addAttribute("message", "Add Result");
            model.addAttribute("submessage1", "Add Result");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/tests/" + testId + "/results/add");
            List<Student> students = studentDao.getAllByCourseId(test.getCourse().getCourseId());
            model.addAttribute("students", students);
            int maximumMarks = testDao.getMaximumMarks(testId);
            model.addAttribute("maximumMarks", maximumMarks);
            return "result/addEditResult";
        }
        resultDao.save(result);
        return "redirect:/" + role + "/academics/tests/" + testId + "/results";
    }

    @GetMapping({ "/admin/academics/tests/{testId}/results/ST{studentId}", "/staff/academics/tests/{testId}/results/ST{studentId}" })
    public String viewResult(@PathVariable("testId") int testId, @PathVariable("studentId") int studentId,
            Model model) {
        model.addAttribute("title", "Academic Portal - Results");
        model.addAttribute("message", "View Result");
        model.addAttribute("submessage1", "Result Details");
        Result result = resultDao.get(testId, studentId);
        model.addAttribute("result", result);
        int maximumMarks = testDao.getMaximumMarks(testId);
        model.addAttribute("maximumMarks", maximumMarks);
        return "result/viewResult";
    }

    @GetMapping({ "/admin/academics/tests/{testId}/results/ST{studentId}/edit", "/staff/academics/tests/{testId}/results/ST{studentId}/edit" })
    public String editResult(@PathVariable("testId") int testId, @PathVariable("studentId") int studentId,
            Model model) {
        String role = securityService.findLoggedInUserRole();
        model.addAttribute("title", "Academic Portal - Results");
        model.addAttribute("message", "Edit Result");
        model.addAttribute("submessage1", "Edit Result");
        model.addAttribute("buttonmessage", "Finish");
        model.addAttribute("submiturl", "/" + role + "/academics/tests/" + testId + "/results/ST" + studentId + "/edit");
        model.addAttribute("edit", "true");
        Result result = resultDao.get(testId, studentId);
        model.addAttribute("result", result);
        int maximumMarks = testDao.getMaximumMarks(testId);
        model.addAttribute("maximumMarks", maximumMarks);
        return "result/addEditResult";
    }

    @PostMapping({ "/admin/academics/tests/{testId}/results/ST{studentId}/edit", "/staff/academics/tests/{testId}/results/ST{studentId}/edit" })
    public String editResult(@PathVariable("testId") int testId, @PathVariable("studentId") int studentId,
            @Valid @ModelAttribute("result") Result result, BindingResult bindingResult, Model model) {
        Test test = testDao.get(result.getTestId());
        if (result.getMarksScored() > test.getMaximumMarks()) {
            bindingResult.rejectValue("marksScored", "Invalid.result.marksScored");
        }
        String role = securityService.findLoggedInUserRole();
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Results");
            model.addAttribute("message", "Edit Result");
            model.addAttribute("submessage1", "Edit Result");
            model.addAttribute("buttonmessage", "Finish");
            model.addAttribute("submiturl", "/" + role + "/academics/tests/" + testId + "/results/ST" + studentId + "/edit");
            model.addAttribute("edit", "true");
            int maximumMarks = testDao.getMaximumMarks(testId);
            model.addAttribute("maximumMarks", maximumMarks);
            return "result/addEditResult";
        }
        Student student = result.getStudent();
        student.setStudentId(studentId);
        result.setStudent(student);

        if (role.equals("staff")) {
            resultDao.updateMarks(result);
        }
        else {
            resultDao.update(result);
        }
        return "redirect:/" + role + "/academics/tests/" + testId + "/results/ST" + studentId;
    }

    @GetMapping({ "/admin/academics/tests/{testId}/results/ST{studentId}/delete", "/staff/academics/tests/{testId}/results/ST{studentId}/delete" })
    public ResponseEntity<Integer> deleteResult(@PathVariable("testId") int testId,
            @PathVariable("studentId") int studentId, Model model) {
        resultDao.delete(testId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({ "/admin/academics/tests/{testId}/results-recheck", "/staff/academics/tests/{testId}/results-recheck" })
    public String listRechecks(@PathVariable("testId") int testId, Model model) {
        model.addAttribute("title", "Academic Portal - Results Recheck");
        model.addAttribute("message", "View all the recheck applied by students");
        List<Result> results = resultDao.getAllRechecksByTestId(testId);
        model.addAttribute("results", results);
        int maximumMarks = testDao.getMaximumMarks(testId);
        model.addAttribute("maximumMarks", maximumMarks);
        return "result/listRechecks";
    }

    @GetMapping("/student/academics/tests/{testId}/results-recheck")
    public String studentRechecks(@PathVariable("testId") int testId, Model model) {
            checkStudentAppearedInTest(testId);
        model.addAttribute("title", "Academic Portal - Results Recheck");
        model.addAttribute("message", "View Recheck Status or Apply for Recheck");
        model.addAttribute("submessage1", "Recheck Details");
        model.addAttribute("submiturl", "/student/academics/tests/" + testId + "/results-recheck");
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        Result result = resultDao.get(testId, studentId);
        model.addAttribute("result", result);
        int maximumMarks = testDao.getMaximumMarks(testId);
        model.addAttribute("maximumMarks", maximumMarks);
        return "result/viewStudentRecheck";
    }

    @PostMapping("/student/academics/tests/{testId}/results-recheck")
    public String studentRechecks(@PathVariable("testId") int testId, @Valid @ModelAttribute("result") Result result,
            BindingResult bindingResult, Model model) {
        checkStudentAppearedInTest(testId);
        int userId = securityService.findLoggedInUserId();
        int studentId = studentDao.getStudentIdByUserId(userId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Academic Portal - Results Recheck");
            model.addAttribute("message", "View Recheck Status or Apply for Recheck");
            model.addAttribute("submessage1", "Recheck Details");
            model.addAttribute("submiturl", "/student/academics/tests/" + testId + "/results-recheck");
            int maximumMarks = testDao.getMaximumMarks(testId);
            model.addAttribute("maximumMarks", maximumMarks);
            return "result/viewStudentRecheck";
        }
        resultDao.applyForRecheck(testId, studentId, result.getRecheckComments());
        return "redirect:/student/academics/tests/" + testId + "/results-recheck";
    }

    @PostMapping({ "/admin/academics/tests/{testId}/results-recheck/{studentId}",
            "/staff/academics/tests/{testId}/results-recheck/{studentId}" })
    public ResponseEntity<String> updateResultAndMarkDone(@PathVariable("testId") int testId,
            @PathVariable("studentId") int studentId, @RequestParam int newMarks, Model model) {
        int maximumMarks = testDao.getMaximumMarks(testId);
        if (newMarks > maximumMarks) {
            return new ResponseEntity<>("Marks scored cannot be greater than maximum marks", HttpStatus.BAD_REQUEST);
        }
        resultDao.updateMarksAndMarkDone(testId, studentId, newMarks);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
