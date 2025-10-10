package com.aston.homework.dao;

import com.aston.homework.dao.impl.UserDAOImpl;

public class DAOProvider {
    private static final UserDAO userDAO = new UserDAOImpl();

    private DAOProvider() {
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }
}
