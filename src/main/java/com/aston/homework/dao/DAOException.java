package com.aston.homework.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(DAOException.class);
    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
        logger.error("DAOException: {}", message, this);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
