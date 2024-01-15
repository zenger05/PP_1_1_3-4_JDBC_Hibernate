package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        final String sqlCreateTable = """
                CREATE TABLE IF NOT EXISTS users(
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                lastName VARCHAR(100) NOT NULL,
                age TINYINT NOT NULL
                )
                """;
        try (Session session = sessionFactory.getCurrentSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(sqlCreateTable).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    }

    @Override
    public void dropUsersTable() {
        final String sqlDropUserTable = """
                DROP TABLE IF EXISTS new.users 
                """;
        try (Session session = sessionFactory.getCurrentSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(sqlDropUserTable).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                users = session.createQuery("from User").getResultList();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }


}