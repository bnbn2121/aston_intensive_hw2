package com.aston.homework.service.impl;

import com.aston.homework.dao.DAOException;
import com.aston.homework.dao.UserDAO;
import com.aston.homework.entity.User;
import com.aston.homework.service.UserService;
import com.aston.homework.service.UserServiceException;

public class UserServiceIpml implements UserService {
    private UserDAO userDAO;

    public UserServiceIpml(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(int id) throws UserServiceException {
        try {
            return userDAO.findUserById(id).orElseThrow(() -> new UserServiceException("user not found"));
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public User addUser(User user) throws UserServiceException {
        try {
            return userDAO.saveUser(user);
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public boolean updateUser(User user) throws UserServiceException {
        try {
            return userDAO.updateUser(user);
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public boolean deleteUser(int id) throws UserServiceException {
        try {
            return userDAO.deleteUser(id);
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public User createUser(String name, String email, int age) throws UserServiceException {
        validateUserData(name, email, age);
        return new User(name, email, age);
    }

    public void validateUserData(String name, String email, int age) throws UserServiceException {
        if (name == null || name.isBlank()) {
            throw new UserServiceException("user name cannot be empty");
        }

        if (email == null || email.isBlank()) {
            throw new UserServiceException("email cannot be empty");
        } else if (!email.contains("@")) {
            throw new UserServiceException("email must comtains \"@\"");
        }

        int minAge = 0;
        int maxAge = 130;
        if (age < minAge || age > maxAge) {
            throw new UserServiceException("age must be between %d and %d".formatted(minAge, maxAge));
        }
    }
}
