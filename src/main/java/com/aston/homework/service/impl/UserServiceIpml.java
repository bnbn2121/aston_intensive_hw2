package com.aston.homework.service.impl;

import com.aston.homework.dao.DAOException;
import com.aston.homework.dao.UserDAO;
import com.aston.homework.entity.User;
import com.aston.homework.service.UserService;
import com.aston.homework.service.UserServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceIpml implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceIpml.class);
    private UserDAO userDAO;

    public UserServiceIpml(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(int id) throws UserServiceException {
        logger.info("Finding user by id: {}", id);
        try {
            return userDAO.findUserById(id).orElseThrow(() -> new UserServiceException("user not found"));
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public User addUser(User user) throws UserServiceException {
        logger.info("Saving new user");
        try {
            return userDAO.saveUser(user);
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public boolean updateUser(User user) throws UserServiceException {
        logger.info("Updating user with id={}", user.getId());
        try {
            return userDAO.updateUser(user);
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public boolean deleteUser(int id) throws UserServiceException {
        logger.info("Deleting user with id={}", id);
        try {
            return userDAO.deleteUser(id);
        } catch (DAOException e) {
            throw new UserServiceException(e);
        }
    }

    public User createUser(String name, String email, int age) throws UserServiceException {
        logger.info("Creating new user with name={}, email={}, age={}", name, email, age);
        validateUserData(name, email, age);
        return new User(name, email, age);
    }

    public void validateUserData(String name, String email, int age) throws UserServiceException {
        String message = null;
        if (name == null || name.isBlank()) {
            message = "user name cannot be empty";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }

        if (email == null || email.isBlank()) {
            message = "email cannot be empty";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        } else if (!email.contains("@")) {
            message = "email must contains \"@\"";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }

        int minAge = 0;
        int maxAge = 130;
        if (age < minAge || age > maxAge) {
            message = "age must be between %d and %d".formatted(minAge, maxAge);
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }
    }
}
