package com.aston.homework.dao.impl;

import com.aston.homework.dao.DAOException;
import com.aston.homework.dao.UserDAO;
import com.aston.homework.dao.util.HibernateUtil;
import com.aston.homework.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {


    @Override
    public Optional<User> findUserById(int id) throws DAOException {
        validate(id);
        try (Session session = HibernateUtil.getSession()) {
            User user = session.find(User.class, id);
            return Optional.ofNullable(user);
        } catch (HibernateException e) {
            throw new DAOException("error finding user", e);
        }
    }

    public Optional<User> findUserByFields(String name, String email, int age) throws DAOException {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT u FROM User u WHERE u.name = :name AND u.email = :email AND u.age = :age";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            query.setParameter("age", age);
            return query.uniqueResultOptional();
        } catch (HibernateException e) {
            throw new DAOException("error finding user", e);
        }
    }

    @Override
    public User saveUser(User user) throws DAOException {
        validate(user);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            Optional<User> foundedUser = findUserByFields(user.getName(), user.getEmail(), user.getAge());
            if (foundedUser.isPresent()) {
                throw new DAOException("such user exists");
            }
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DAOException("error saving user", e);
        }
    }

    @Override
    public boolean updateUser(User user) throws DAOException {
        validate(user);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            User userFromDB = session.find(User.class, user.getId());
            if (userFromDB == null) {
                throw new DAOException("user not found");
            }
            transaction = session.beginTransaction();
            userFromDB.setName(user.getName());
            userFromDB.setEmail(user.getEmail());
            userFromDB.setAge(user.getAge());
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
                throw new DAOException("user not found");
            }
            transaction = session.beginTransaction();
            session.remove(userFromDB);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DAOException("error deleting user", e);
        }
    }

    private void validate(int id) throws DAOException {
        if (id <= 0) {
            throw new DAOException("id cannot be <= 0");
        }
    }

    private void validate(User user) throws DAOException {
        if (user == null) {
            throw new DAOException("user cannot be null");
        }
        if (user.getName() == null) {
            throw new DAOException("user name cannot be null");
        }
        if (user.getEmail() == null) {
            throw new DAOException("user email cannot be null");
        }
    }
}

