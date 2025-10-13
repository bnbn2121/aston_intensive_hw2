package com.aston.homework.ui;

import com.aston.homework.controller.ControllerException;
import com.aston.homework.controller.ControllerImpl;
import com.aston.homework.entity.User;
import com.aston.homework.ui.util.ConsoleUtil;

public class ConsoleUI {
    private ControllerImpl controller;
    private boolean isExit = false;

    public ConsoleUI(ControllerImpl controller) {
        this.controller = controller;
    }

    public void runMenu() {
        while (!isExit) {
            mainMenu();
        }
    }

    private void mainMenu() {
        showMenu();
        int input = ConsoleUtil.userIntInput(null, 0, 4);
        String response;
        try {
            response = switch (input) {
                case 1 -> addNewUser();
                case 2 -> showUserById();
                case 3 -> removeUserById();
                case 4 -> updateUserById();
                case 0 -> exitApp();
                default -> null;
            };
        } catch (ControllerException e) {
            response = e.getMessage();
        }
        System.out.println();
        System.out.println(response);
        System.out.println();
    }

    private void showMenu() {
        System.out.println("=== Main menu ===");
        System.out.println("1. Add new user");
        System.out.println("2. Show user by id");
        System.out.println("3. Remove user by id");
        System.out.println("4. Update user by id");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
    }

    private String addNewUser() throws ControllerException {
        String name = ConsoleUtil.userStringInput("Set user name:");
        String email = ConsoleUtil.userStringInput("Set user email:");
        int age = ConsoleUtil.userIntInput("Set user age:", 0, 130);
        User newUser = controller.createAndSaveNewUser(name, email, age);
        return "Success! User added with id = %d".formatted(newUser.getId());
    }

    private String showUserById() throws ControllerException {
        int id = ConsoleUtil.userIntInput("Set user id:", 0, 1000);
        User user = controller.getUserById(id);
        return user.toString();
    }

    private String removeUserById() throws ControllerException {
        int id = ConsoleUtil.userIntInput("Set user id to remove:", 0, 1000);
        if (controller.removeUserById(id)) {
            return "Success! User with id = %d is deleted".formatted(id);
        }
        return "The operation was unsuccessful";

    }

    private String updateUserById() throws ControllerException {
        int id = ConsoleUtil.userIntInput("Set user id to update:", 0, 1000);
        System.out.println(controller.getUserById(id));
        System.out.println("Update user data please");
        String newName = ConsoleUtil.userStringInput("Set new user name:");
        String newEmail = ConsoleUtil.userStringInput("Set new user email:");
        int newAge = ConsoleUtil.userIntInput("Set new user age:", 0, 130);
        User updatedUser = controller.updateUserById(id, newName, newEmail, newAge);
        return "Success! User data is updated:\n" + updatedUser.toString();
    }

    private String exitApp() {
        isExit = true;
        return "Application is finished";
    }
}
