package com.aston.homework.ui;

import com.aston.homework.controller.ControllerImpl;
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
        String response = switch (input) {
            case 1 -> addNewUser();
            case 2 -> showUserById();
            case 3 -> removeUserById();
            case 4 -> updateUserById();
            case 0 -> exitApp();
            default -> null;
        };
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

    private String addNewUser() {
        String name = ConsoleUtil.userStringInput("Set user name:");
        String email = ConsoleUtil.userStringInput("Set user email:");
        int age = ConsoleUtil.userIntInput("Set user age:", 0, 130);
        return controller.createAndSaveNewUser(name, email, age);
    }

    private String showUserById() {
        int id = ConsoleUtil.userIntInput("Set user id:", 0, 1000);
        return controller.getUserById(id);
    }

    private String removeUserById() {
        int id = ConsoleUtil.userIntInput("Set user id to remove:", 0, 1000);
        return controller.removeUserById(id);
    }

    private String updateUserById() {
        int id = ConsoleUtil.userIntInput("Set user id to update:", 0, 1000);
        System.out.println(controller.getUserById(id));
        System.out.println("Update user data please");
        String newName = ConsoleUtil.userStringInput("Set new user name:");
        String newEmail = ConsoleUtil.userStringInput("Set new user email:");
        int newAge = ConsoleUtil.userIntInput("Set new user age:", 0, 130);
        return controller.updateUserById(id, newName, newEmail, newAge);
    }

    private String exitApp() {
        isExit = true;
        return "Application is finished";
    }
}
