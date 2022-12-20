package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            t = session.beginTransaction();
            session.save(user);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction t = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            t = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        Transaction t = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            t = session.beginTransaction();
            users = session.createQuery("from User")
                    .getResultList();
            for (User u : users)
                System.out.println(u);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("truncate table users").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
