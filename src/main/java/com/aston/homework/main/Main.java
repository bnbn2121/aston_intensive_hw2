package com.aston.homework.main;

import com.aston.homework.controller.ControllerImpl;
import com.aston.homework.dao.impl.UserDAOImpl;
import com.aston.homework.service.impl.UserServiceIpml;
import com.aston.homework.ui.ConsoleUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ConsoleUI consoleUI = new ConsoleUI(new ControllerImpl(new UserServiceIpml(new UserDAOImpl())));
            consoleUI.runMenu();
        } catch (Exception e) {
            logger.error("Error in application", e);
            System.exit(1);
        }

    }
}
