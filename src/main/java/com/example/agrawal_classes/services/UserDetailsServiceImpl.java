package com.example.agrawal_classes.services;

import com.example.agrawal_classes.dao.UserDao;
import com.example.agrawal_classes.model.MyUserDetails;
import com.example.agrawal_classes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }
        user = userDao.setLoginTimestamp(user);
        return new MyUserDetails(user);
    }

}
