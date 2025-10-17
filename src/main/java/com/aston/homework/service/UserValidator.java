package com.aston.homework.service;

import com.aston.homework.entity.User;
import com.aston.homework.service.impl.UserServiceIpml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserValidator {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceIpml.class);

    public void validateData(String name, String email, int age) throws UserServiceException {
        String message = null;
        if (name == null || name.isBlank()) {
            message = "user name cannot be empty";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }

        if (email == null || email.isBlank()) {
            message = "email cannot be empty";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        } else if (!email.contains("@")) {
            message = "email must contains \"@\"";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }

        int minAge = 0;
        int maxAge = 130;
        if (age < minAge || age > maxAge) {
            message = "age must be between %d and %d".formatted(minAge, maxAge);
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }
    }

    public void validateData(User user) throws UserServiceException {
        String message = null;
        if (user.getName() == null || user.getName().isBlank()) {
            message = "user name cannot be empty";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            message = "email cannot be empty";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        } else if (!user.getEmail().contains("@")) {
            message = "email must contains \"@\"";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }

        int minAge = 0;
        int maxAge = 130;
        if (user.getAge() < minAge || user.getAge() > maxAge) {
            message = "age must be between %d and %d".formatted(minAge, maxAge);
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }
    }

    public void validateId(int id) throws UserServiceException {
        if (id <= 0) {
            String message = "id cannot be <0";
            logger.info("validation unsuccess: {}", message);
            throw new UserServiceException(message);
        }
    }

    public void normalizeEmail(User user) {
        if (user != null) {
            user.setEmail(user.getEmail().toLowerCase());
        }
    }

    public String normalizeEmail(String email) {
        if (email != null) {
            return email.toLowerCase();
        }
        return null;
    }
}
