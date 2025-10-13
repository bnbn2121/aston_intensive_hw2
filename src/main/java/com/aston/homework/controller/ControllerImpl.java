package com.aston.homework.controller;


import com.aston.homework.entity.User;
import com.aston.homework.service.UserService;
import com.aston.homework.service.UserServiceException;

public class ControllerImpl {
    private UserService userService;

    public ControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public User createAndSaveNewUser(String name, String email, int age) throws ControllerException {
        try {
            User newUser = userService.createUser(name, email, age);
            return userService.addUser(newUser);
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }

    public User getUserById(int id) throws ControllerException {
        try {
            return userService.getUserById(id);
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }

    public boolean removeUserById(int id) throws ControllerException {
        try {
            return userService.deleteUser(id);
        } catch (UserServiceException e) {
            throw new ControllerException(e);
        }
    }
    public User updateUserById(int id, String name, String email, int age) throws ControllerException {
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
