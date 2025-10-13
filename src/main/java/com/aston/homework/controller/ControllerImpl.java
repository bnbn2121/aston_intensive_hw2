package com.aston.homework.controller;


import com.aston.homework.entity.User;
import com.aston.homework.service.UserService;
import com.aston.homework.service.UserServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerImpl {
    private static final Logger logger = LoggerFactory.getLogger(ControllerImpl.class);
    private UserService userService;

    public ControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public User createAndSaveNewUser(String name, String email, int age) throws ControllerException {
        logger.info("Creating user and saving user with name={}, email={}, age={}", name, email, age);
        try {
            User newUser = userService.createUser(name, email, age);
            return userService.addUser(newUser);
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }

    public User getUserById(int id) throws ControllerException {
        logger.info("Finding user by id: {}", id);
        try {
            return userService.getUserById(id);
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }

    public boolean removeUserById(int id) throws ControllerException {
        logger.info("Deleting user with id={}", id);
        try {
            return userService.deleteUser(id);
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }
    public User updateUserById(int id, String name, String email, int age) throws ControllerException {
        logger.info("Updating user with id={}", id);
        try {
            userService.validateUserData(name, email, age);

            User userForUpdate = userService.getUserById(id);
            userForUpdate.setName(name);
            userForUpdate.setEmail(email);
            userForUpdate.setAge(age);
            userService.updateUser(userForUpdate);
            return userForUpdate;
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }
}
