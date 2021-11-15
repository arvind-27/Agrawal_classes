package com.example.agrawal_classes.controllers;

import com.example.agrawal_classes.dao.UserDao;
import com.example.agrawal_classes.dao.UserTokenDao;
import com.example.agrawal_classes.model.User;
import com.example.agrawal_classes.services.SecurityService;
import com.example.agrawal_classes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenDao userTokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/user/login")
    public String login(Model model, String error, String logout, String verified, String resetSuccessful, String emailSent, String verificationEmailSent) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("errormessage", "Either the username and password combination is invalid, or the email is not verified");
        if (logout != null)
            model.addAttribute("successmessage", "You have been logged out successfully.");
        if (verified != null)
            model.addAttribute("successmessage", "Your email has been verified successfully.");
        if (emailSent != null)
            model.addAttribute("successmessage", "Your password reset mail has been sent");
        if (resetSuccessful != null)
            model.addAttribute("successmessage", "Your password has been reset successfully");
        if (verificationEmailSent != null)
            model.addAttribute("successmessage", "Verification email has been sent on your email address");
        model.addAttribute("title", "Login Page");
        return "user/login";
    }

    @GetMapping("/user/verify-email")
    public String verifyEmail(Model model, String token) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        userService.verifyEmail(token);
        return "redirect:/user/login?verified";
    }

    @GetMapping("/user/confirm-registration")
    public String confirmRegistration(Model model, String token) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        Integer userId = userTokenDao.getUserIdByToken(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }
        model.addAttribute("title", "Confirm Registration");
        model.addAttribute("message", "Set your password and verify email");
        model.addAttribute("submitUrl", "/user/confirm-registration?token=" + token);
        return "user/confirmRegistration";
    }

    @PostMapping("/user/confirm-registration")
    public String confirmRegistration(@RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword, Model model, String token) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        Integer userId = userTokenDao.getUserIdByToken(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("title", "Confirm Registration");
            model.addAttribute("message", "Set your password and verify email");
            model.addAttribute("errormessage", "The passwords do not match");
            model.addAttribute("submitUrl", "/user/confirm-registration?token=" + token);
            return "user/confirmRegistration";
        }
        userService.confirmRegistration(token, password);
        return "redirect:/user/login?verified";
    }

    @GetMapping("/user/reset-password")
    public String resetPassword(Model model, String token) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        Integer userId = userTokenDao.getUserIdByToken(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }
        model.addAttribute("title", "Reset Password");
        model.addAttribute("message", "Set your password");
        model.addAttribute("submitUrl", "/user/reset-password?token=" + token);
        return "user/confirmRegistration";
    }

    @PostMapping("/user/reset-password")
    public String resetPassword(@RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword, Model model, String token) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        Integer userId = userTokenDao.getUserIdByToken(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("title", "Reset Password");
            model.addAttribute("message", "Set your password");
            model.addAttribute("errormessage", "The passwords do not match");
            model.addAttribute("submitUrl", "/user/reset-password?token=" + token);
            return "user/confirmRegistration";
        }
        userService.resetPassword(token, password);
        return "redirect:/user/login?resetSuccessful";
    }

    @GetMapping("/user/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        model.addAttribute("message", "Get password reset email");
        model.addAttribute("submitUrl", "/user/forgot-password");
        return "user/forgotPassword";
    }

    @PostMapping("/user/forgot-password")
    public String resetPassword(@RequestParam("email") String email, Model model) {
        if (securityService.findLoggedInUserId() != 0) {
            return "redirect:/";
        }
        User user = userDao.findByEmailAddress(email);
        if (user == null) {
            model.addAttribute("title", "Forgot Password");
            model.addAttribute("message", "Get password reset email");
            model.addAttribute("errormessage", "The email does not exist");
            model.addAttribute("submitUrl", "/user/forgot-password");
            return "user/forgotPassword";
        }
        userService.sendPasswordResetEmail(user);
        return "redirect:/user/login?emailSent";
    }

    @GetMapping("/user/updateSessionIfRequired")
    public ResponseEntity<Integer> isUserLoggedIn(Model model) {
        boolean isDeletedOrUpdated = securityService.isUserDeletedOrUpdated();
        if (!isDeletedOrUpdated)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
