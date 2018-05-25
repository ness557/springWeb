package ness.service;

import ness.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    void updateUser(User user);
    void removeUser(User user);
    void saveOrUpdate(User user);
    void removeUser(int id);
    User getUserById(int id);
    List<User> getUserList();
}
