package com.rcuk.portal.service;

import com.rcuk.portal.dao.UserDao;
import com.rcuk.portal.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private String host;

    public User findById(String id) {
        return userDao.findById(id).orElse(null);
    }

    public List<User> findByIds(List<String> ids) {
        return userDao.findAllByIdIn(ids);
    }


    public User findUserByUsername(String email) {
        return userDao.findDistinctByEmail(email);
    }

    public User getLoggedInUser() {

        try {
            return findUserByUsername(
                    ((org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getPrincipal())
                            .getUsername());
        } catch (Exception e) {

        }
        return null;
    }

}
