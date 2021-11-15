package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.UserDao;
import com.example.agrawal_classes.dao.UserPhoneNumberDao;
import com.example.agrawal_classes.model.User;
import com.example.agrawal_classes.model.UserPhoneNumber;
import com.example.agrawal_classes.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Transactional
@Controller
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPhoneNumberDao userPhoneNumberDao;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/admin/users")
    public String usersPortal(Model model) {
        model.addAttribute("title", "Users Portal");
        model.addAttribute("message", "View all the users");
        List<User> users = userDao.getAll();
        model.addAttribute("users", users);
        return "user/usersPortal";
    }

    public void validateCorrectUserForStaff(int userId) {
        String role = securityService.findLoggedInUserRole();
        if (role.equals("staff")) {
            User user = userDao.get(userId);
            if (!user.getRole().equals("ROLE_STUDENT"))
                throw new AccessDeniedException("You are not allowed to delete/activate this user");
        }
    }

    @GetMapping({ "/admin/users/{userId}/activate", "/staff/users/{userId}/activate"  })
    public ResponseEntity<Integer> activateUser(@PathVariable("userId") int userId, Model model) {
        validateCorrectUserForStaff(userId);
        userDao.activate(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({ "/admin/users/{userId}/delete", "/staff/users/{userId}/delete" })
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId, Model model) {
        validateCorrectUserForStaff(userId);
        if (userId == securityService.findLoggedInUserId()) {
            return new ResponseEntity<>("Can't delete own user", HttpStatus.BAD_REQUEST);
        }
        userDao.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/users/{userId}/phoneNumber/add")
    public ResponseEntity<String> addUserPhoneNumber(@PathVariable("userId") int userId,
            @RequestParam String phoneNumber, Model model) {
        if (!phoneNumber.matches("^[1-9][0-9]{9,9}$")) {
            return new ResponseEntity<>("The phone number must be of 10 digits", HttpStatus.BAD_REQUEST);
        }
        int loggedInUserId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("admin") && !role.equals("staff") && loggedInUserId != userId)
            return new ResponseEntity<>("You are not allowed to perform this action", HttpStatus.BAD_REQUEST);
        if (loggedInUserId != userId)
            validateCorrectUserForStaff(userId);

        UserPhoneNumber userPhoneNumber = new UserPhoneNumber(phoneNumber, userId);
        try {
            userPhoneNumberDao.save(userPhoneNumber);
        } catch (Exception e) {
            return new ResponseEntity<>("The phone number already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/users/{userId}/phoneNumber/delete")
    public ResponseEntity<String> deleteUserPhoneNumber(@PathVariable("userId") int userId,
            @RequestParam String phoneNumber, Model model) {
        int loggedInUserId = securityService.findLoggedInUserId();
        String role = securityService.findLoggedInUserRole();
        if (!role.equals("admin") && !role.equals("staff") && loggedInUserId != userId)
            return new ResponseEntity<>("You are not allowed to perform this action", HttpStatus.BAD_REQUEST);
        if (loggedInUserId != userId)
            validateCorrectUserForStaff(userId);

        UserPhoneNumber userPhoneNumber = new UserPhoneNumber(phoneNumber, userId);
        userPhoneNumberDao.delete(userPhoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
