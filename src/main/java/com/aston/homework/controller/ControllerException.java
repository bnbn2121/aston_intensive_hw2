package com.aston.homework.controller;

import com.aston.homework.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(DAOException.class);
    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
        logger.error("ControllerException: {}", message, this);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause.getMessage(), cause);
        logger.error(cause.getMessage(), cause);
    }
}
