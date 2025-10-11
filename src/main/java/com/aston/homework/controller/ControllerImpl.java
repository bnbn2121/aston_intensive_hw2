package com.aston.homework.controller;


import com.aston.homework.entity.User;
import com.aston.homework.service.UserService;
import com.aston.homework.service.UserServiceException;

public class ControllerImpl {
    private UserService userService;

    public ControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public String createAndSaveNewUser(String name, String email, int age) {
        try {
            User newUser = userService.createUser(name, email, age);
            userService.addUser(newUser);
            return "Success! User added with id = %d".formatted(newUser.getId());
        } catch (UserServiceException e) {
            return e.getMessage();
        }
    }

    public String getUserById(int id) {
        try {
            User user = userService.getUserById(id);
            return user.toString();
        } catch (UserServiceException e) {
            return e.getMessage();
        }
    }

    public String removeUserById(int id) {
        try {
            userService.deleteUser(id);
            return "Success! User with id = %d is deleted".formatted(id);
        } catch (UserServiceException e) {
            return e.getMessage();
        }
    }
    public String updateUserById(int id, String name, String email, int age) {
        try {
            userService.validateUserData(name, email, age);

            User userForUpdate = userService.getUserById(id);
            userForUpdate.setName(name);
            userForUpdate.setEmail(email);
            userForUpdate.setAge(age);
            userService.updateUser(userForUpdate);
            return "Success! User data is updated";
        } catch (UserServiceException e) {
            return e.getMessage();
        }
    }
}
