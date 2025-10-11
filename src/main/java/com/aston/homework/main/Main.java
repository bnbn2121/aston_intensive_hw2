package com.aston.homework.main;

import com.aston.homework.controller.ControllerImpl;
import com.aston.homework.dao.impl.UserDAOImpl;
import com.aston.homework.service.impl.UserServiceIpml;
import com.aston.homework.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI(new ControllerImpl(new UserServiceIpml(new UserDAOImpl())));
        consoleUI.runMenu();
    }
}
