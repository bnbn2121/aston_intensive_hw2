package com.aston.homework.service;

import com.aston.homework.dao.DAOException;
import com.aston.homework.entity.User;

public interface UserService {
    User getUserById(int id) throws UserServiceException;
    User addUser(User user) throws UserServiceException;
    boolean updateUser(User user) throws UserServiceException;
    boolean deleteUser(int id) throws UserServiceException;
    User createUser(String name, String email, int age) throws UserServiceException;
    void validateUserData(String name, String email, int age) throws UserServiceException;
}
