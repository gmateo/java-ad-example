package com.aw.ad;

import java.util.List;

/**
 * User: gmc
 * Date: 16/02/11
 */
public interface UserDao {

    public List getAllUsers();

    public boolean login(String userName, String password);

    public void enableUser(String userName);

    public void disableUser(String userName);

    public void changePassword(String userName, String password);

    public void createUser(User user);

    public void addUserToGroup(String userName, String group);

}