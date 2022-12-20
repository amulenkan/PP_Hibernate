package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        // реализуйте алгоритм здесь
        Util util = new Util();
        util.getSessionFactory();
        UserDao userDao = new UserDaoHibernateImpl();
        //UserService userService = new UserServiceImpl();

        //userDao.createUsersTable();
        //userDao.cleanUsersTable();
        //userDao.saveUser("Ivan", "Ivanov", (byte) 25);
        userDao.removeUserById(1);
        //userDao.getAllUsers();
        //userDao.dropUsersTable();
        /*userService.saveUser("Иван", "Иванов", (byte) 21);
        userService.saveUser("Мария", "Смирнова", (byte) 25);
        userService.saveUser("Петр", "Сидоров", (byte) 26);
        userService.saveUser("Степан", "Кузнецов", (byte) 27);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();*/
        //userService.dropUsersTable();
    }
}
