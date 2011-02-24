package com.aw.ad;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("springldap.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        System.out.println(factory.toString());
        UserDao userDao = (UserDaoImpl) factory.getBean("ldapUser");
        String userName = "Test19";
        String pwd = "##12345xx";
        // Creating a User
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(pwd);
        userDao.createUser(newUser);
        // Disabling user
        userDao.disableUser(userName);
        // Login user
        System.out.println("Login is not possible for disabled users. Result:" + userDao.login(userName, pwd));
        // Enabling user
        userDao.enableUser(userName);
        // Login user
        System.out.println("Login Successful. Result:" + userDao.login(userName, pwd));
        // Login with wrong password
        System.out.println("Login is not possible with wrong password. Result:" + userDao.login(userName, "121212"));
        // Changing Password
        String newPwd = "##xx1234";
        userDao.changePassword(userName, newPwd);
        // Login user
        System.out.println("Login Successful. Result:" + userDao.login(userName, newPwd));
        // Adding the user to a Group
        userDao.addUserToGroup(userName, "AWGrupo1");
        // Showing all users
        List<User> allUsers = userDao.getAllUsers();
        System.out.println("There are:[" + allUsers.size() + "] users");
        for (User user : allUsers) {
            System.out.println(user);
        }
    }
}