package com.aston.homework.dao.impl;

import com.aston.homework.dao.DAOException;
import com.aston.homework.dao.UserDAO;
import com.aston.homework.dao.util.HibernateUtil;
import com.aston.homework.entity.User;
import com.aston.homework.service.UserServiceException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public Optional<User> findUserById(int id) throws DAOException {
        validate(id);
        try (Session session = HibernateUtil.getSession()) {
            User user = session.find(User.class, id);
            if (user == null) {
                logger.info("user is not founded in DB");
            } else {
                logger.info("user founded in DB successfully");
            }
            return Optional.ofNullable(user);
        } catch (HibernateException e) {
            logger.error("error finding user in DB");
            throw new DAOException("error finding user", e);
        }
    }

    public Optional<User> findUserByEmail(String email) throws DAOException {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT u FROM User u WHERE u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            Optional<User> optionalUser = query.uniqueResultOptional();
            if (optionalUser.isPresent()) {
                logger.info("user founded in DB with id={}", optionalUser.get().getId());
            } else {
                logger.info("user is not founded in DB");
            }
            return optionalUser;
        } catch (HibernateException e) {
            logger.error("error finding user in DB");
            throw new DAOException("error finding user", e);
        }
    }

    public boolean existsByEmail(String email) throws DAOException {
        return findUserByEmail(email).isPresent();
    }

    @Override
    public User saveUser(User user) throws DAOException {
        validate(user);
        Transaction transaction = null;
        if (existsByEmail(user.getEmail())) {
            logger.info("saving user in DB is unavailable, such email exists");
            throw new DAOException("saving is unavailable, such email exists");
        }
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            logger.info("saving user in DB is success");
            return user;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("error saving user in DB");
            throw new DAOException("error saving user", e);
        }
    }

    @Override
    public boolean updateUser(User user) throws DAOException {
        validate(user);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            User userFromDB = session.find(User.class, user.getId());
            if (userFromDB == null) {
                logger.info("user is not founded in DB");
                throw new DAOException("user not found");
            }
            if(!user.getEmail().equals(userFromDB.getEmail())) {
                Optional<User> optionalUserCheckedEmail = findUserByEmail(user.getEmail());
                if(optionalUserCheckedEmail.isPresent()) {
                    logger.info("this email already used");
                    throw new DAOException("this email already used");
                }
            }
            userFromDB.setName(user.getName());
            userFromDB.setEmail(user.getEmail());
            userFromDB.setAge(user.getAge());
            transaction.commit();
            logger.info("updating user in DB is success");
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("error updating user in DB");
            throw new DAOException("error updating user", e);
        }
    }

    @Override
    public boolean deleteUser(int id) throws DAOException {
        validate(id);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            User userFromDB = session.find(User.class, id);
            if (userFromDB == null) {
                logger.info("user is not founded in DB");
                throw new DAOException("user not found");
            }
            transaction = session.beginTransaction();
            session.remove(userFromDB);
            transaction.commit();
            logger.info("deleting user from DB is success");
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("error deleting user from DB");
            throw new DAOException("error deleting user", e);
        }
    }

    private void validate(int id) throws DAOException {
        if (id <= 0) {
            logger.debug("id cannot be <= 0");
            throw new DAOException("id cannot be <= 0");
        }
    }

    private void validate(User user) throws DAOException {
        String message;
        if (user == null) {
            message = "user cannot be null";
            logger.debug("DAO validation unsuccess: {}", message);
            throw new DAOException(message);
        }
        if (user.getName() == null) {
            message = "user name cannot be null";
            logger.debug("DAO validation unsuccess: {}", message);
            throw new DAOException(message);
        }
        if (user.getEmail() == null) {
            message = "user email cannot be null";
            logger.debug("DAO validation unsuccess: {}", message);
            throw new DAOException(message);
        }
    }
}

