package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        List<User> userList = new ArrayList<>();
        User user1 = new User("Danial", "Shakhnazarov", (byte) 21);
        User user2 = new User("Masha", "Shakhnazarov", (byte) 9);
        User user3 = new User("Omar", "Shakhnazarov", (byte) 29);
        User user4 = new User("Rasul", "Shakhnazarov", (byte) 51);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        for(User user : userList) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных ");
        }

        userList = userService.getAllUsers();
        System.out.println(userList);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
