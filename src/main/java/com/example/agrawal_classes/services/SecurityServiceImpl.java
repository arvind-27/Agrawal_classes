package com.example.agrawal_classes.services;

import com.example.agrawal_classes.dao.UserDao;
import com.example.agrawal_classes.model.MyUserDetails;
import com.example.agrawal_classes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserDao userDao;

    @Override
    public String findLoggedInUsername() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            return ((MyUserDetails) myUserDetails).getUser().getUsername();
        }
        return null;
    }

    @Override
    public int findLoggedInUserId() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            return ((MyUserDetails) myUserDetails).getUser().getUserId();
        }
        return 0;
    }

    @Override
    public String findLoggedInName() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            User user = ((MyUserDetails) myUserDetails).getUser();
            return user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        }
        return null;
    }

    @Override
    public String findLoggedInUserRole() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            String smallRole = ((MyUserDetails) myUserDetails).getUser().getSmallRole();
            return smallRole;
        }
        return null;
    }

    @Override
    public User findLoggedInUser() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            User user = ((MyUserDetails) myUserDetails).getUser();
            return user;
        }
        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        MyUserDetails myUserDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                myUserDetails, password, myUserDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    @Override
    public void autoLogout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean isUserDeletedOrUpdated() {
        int userId = findLoggedInUserId();
        if (userId == 0)
            return false;
        User user = userDao.get(userId);
        if (user == null) {
            autoLogout();
            return true;
        } else {
            // Resetting the authentication object
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails oldUserDetails = (MyUserDetails)auth.getPrincipal();
            MyUserDetails newUserDetails = new MyUserDetails(user);
            boolean updated = !Objects.equals(user, oldUserDetails.getUser());
            if (updated) {
                Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, auth.getCredentials(),
                        newUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuth);
                return true;
            }
        }
        return false;
    }
}
