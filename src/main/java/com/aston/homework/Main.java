package com.aston.homework;

import com.aston.homework.dao.impl.UserDAOImpl;
import com.aston.homework.dao.util.HibernateUtil;
import com.aston.homework.service.impl.UserServiceIpml;
import com.aston.homework.view.ConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ConsoleView consoleView = new ConsoleView(new UserServiceIpml(new UserDAOImpl(HibernateUtil.getSessionFactory())));
            consoleView.runMenu();
        } catch (Exception e) {
            logger.error("Error in application", e);
            System.exit(1);
        }
    }
}
