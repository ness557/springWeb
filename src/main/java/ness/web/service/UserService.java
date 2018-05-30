package ness.web.service;

import ness.web.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    void updateUser(User user);
    void removeUser(User user);
    void saveOrUpdate(User user);
    void removeUser(int id);
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getUserList();
}
