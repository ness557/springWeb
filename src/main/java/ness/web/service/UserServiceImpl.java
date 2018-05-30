package ness.web.service;

import ness.web.model.User;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Resource(name = "myProperties")
    private Properties properties;

    private static final String getUser = "${host}/users?id=${id}";
    private static final String getUserByUsername = "${host}/users?username=${username}";
    private static final String getUsers = "${host}/users";
    private static final String addUser = "${host}/users";
    private static final String editUser = "${host}/users";
    private static final String removeUser = "${host}/users?id=${id}";

    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void addUser(User user) {
        logger.info("Executing PUT method to add user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(addUser);

        restTemplate.put(req, user);
    }

    @Override
    public void updateUser(User user) {
        logger.info("Executing POST method to update user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(editUser);

        restTemplate.postForEntity(req, user, User.class);
    }


    @Override
    public void removeUser(User user) {
        logger.info("Executing DELETE method to delete user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("id", user.getId());

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeUser);

        restTemplate.delete(req);
    }

    @Override
    public void saveOrUpdate(User user) {
        logger.info("Executing method save or update to user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        if (user.getId() == 0) {
            String req = sub.replace(addUser);
            restTemplate.put(req, user);
        } else {
            String req = sub.replace(editUser);
            restTemplate.postForEntity(req, user, User.class);
        }

    }

    @Override
    public void removeUser(int id) {
        logger.info("Executing DELETE method to delete user by id = " + id);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeUser);

        restTemplate.delete(req);
    }

    @Override
    public User getUserById(int id) {
        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUser);

        return restTemplate.getForObject(req, User.class);
    }

    @Override
    public User getUserByUsername(String username) {
        logger.info("Trying to get user by username = " + username);
        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("username", username);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUserByUsername);

        User user =  restTemplate.getForObject(req, User.class);
        logger.info("Got user: " + user);;
        return user;
    }

    @Override
    public List<User> getUserList() {
        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        valuesMap.get("host");
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUsers);

        logger.info(req);

        return restTemplate.getForObject(req, ArrayList.class);
    }
}
