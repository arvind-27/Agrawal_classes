package com.example.agrawal_classes.services;

import com.example.agrawal_classes.dao.UserDao;
import com.example.agrawal_classes.dao.UserTokenDao;
import com.example.agrawal_classes.model.User;
import com.example.agrawal_classes.model.UserToken;
import com.example.agrawal_classes.utils.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private ServerUtil serverUtil;

    @Autowired
    private UserTokenDao userTokenDao;

    @Override
    public User save(User user) {
        if (user.getPassword() != null)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        return userDao.findByEmailAddress(emailAddress);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public String createOrUpdateToken(int userId) {
        UserToken userToken = new UserToken(userId);
        String token = userTokenDao.getTokenByUserId(userId);
        if (token == null) {
            userTokenDao.save(userToken);
        } else {
            userTokenDao.update(userToken);
        }
        return userToken.getToken();
    }

    @Override
    public User activateUserAndEmailToken(User user) {
        userDao.activate(user.getUserId());

        String email = user.getEmailAddress();
        String subject = "Account Creation Successful | Confirm Registration";
        String message = "";
        String token = createOrUpdateToken(user.getUserId());
        message += "Hello " + user.getName() + ",\n\n";
        message += "Please go to " + serverUtil.getHostAddressAndPort() + "/user/confirm-registration?token=" + token;
        message += " in order to set your password and confirm your registration. You can login using your username '" + user.getUsername() + "'";
        message += " by going to " + serverUtil.getHostAddressAndPort() + "/user/login" + "\n\nThank you!";
        emailSendService.sendEmail(email, subject, message);

        return user;
    }

    @Override
    public void sendVerificationEmail(User user) {
        String email = user.getEmailAddress();
        String subject = "Account Creation Successful | Verify Email";
        String message = "";
        String token = createOrUpdateToken(user.getUserId());
        message += "Hello " + user.getName() + ",\n\n";
        message += "Please go to " + serverUtil.getHostAddressAndPort() + "/user/verify-email?token=" + token;
        message += " to verify your email. Thereafter, you can login using your username '" + user.getUsername() + "'";
        message += " by going to " + serverUtil.getHostAddressAndPort() + "/user/login" + "\n\nThank you!";
        emailSendService.sendEmail(email, subject, message);
    }

    @Override
    public void sendPasswordResetEmail(User user) {
        String email = user.getEmailAddress();
        String subject = "Password Reset Email";
        String message = "";
        String token = createOrUpdateToken(user.getUserId());
        message += "Hello " + user.getName() + ",\n\n";
        message += "Please go to " + serverUtil.getHostAddressAndPort() + "/user/reset-password?token=" + token;
        message += " to reset your password. Thereafter, you can login using your username '" + user.getUsername()
                + "'";
        message += " by going to " + serverUtil.getHostAddressAndPort() + "/user/login" + "\n\nThank you!";
        emailSendService.sendEmail(email, subject, message);
    }

    public int validateToken(String token) {
        Integer userId = userTokenDao.getUserIdByToken(token);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }
        userTokenDao.delete(token);
        return userId;
    }

    @Override
    public void verifyEmail(String token) {
        int userId = validateToken(token);
        userDao.verifyEmail(userId);
    }

    @Override
    public void confirmRegistration(String token, String password) {
        int userId = validateToken(token);
        userDao.changePassword(userId, bCryptPasswordEncoder.encode(password));
        userDao.verifyEmail(userId);
    }

    @Override
    public void resetPassword(String token, String password) {
        int userId = validateToken(token);
        userDao.changePassword(userId, bCryptPasswordEncoder.encode(password));
    }

    @Override
    public void changePassword(int userId, String password) {
        userDao.changePassword(userId, bCryptPasswordEncoder.encode(password));
    }

    @Override
    public boolean verifyOldPassword(int userId, String password) {
        String encryptedPassword = userDao.getPassword(userId);
        return bCryptPasswordEncoder.matches(password, encryptedPassword);
    }

}
