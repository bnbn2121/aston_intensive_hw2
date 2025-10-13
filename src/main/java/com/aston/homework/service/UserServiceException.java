package com.aston.homework.service;

import com.aston.homework.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(DAOException.class);
    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
