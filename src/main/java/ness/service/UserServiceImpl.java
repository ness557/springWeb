package ness.service;

import ness.model.User;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Value("${rest.hosturl}")
    private static String restHostUrl;

    private static final String getUser = "${host}/users?id=${id}";
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
        valuesMap.put("host", restHostUrl);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(addUser);

        restTemplate.put(req, user);
    }

    @Override
    public void updateUser(User user) {
        logger.info("Executing POST method to update user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(editUser);

        restTemplate.postForEntity(req, user, User.class);
    }


    @Override
    public void removeUser(User user) {
        logger.info("Executing DELETE method to delete user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);
        valuesMap.put("id", user.getId());

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeUser);

        restTemplate.delete(req);
    }

    @Override
    public void saveOrUpdate(User user) {
        logger.info("Executing method save or update to user " + user);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);

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
        valuesMap.put("host", restHostUrl);
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeUser);

        restTemplate.delete(req);
    }

    @Override
    public User getUserById(int id) {
        Map valuesMap = new HashMap();

        valuesMap.put("host", restHostUrl);
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUser);

        return restTemplate.getForObject(req, User.class);
    }

    @Override
    public List<User> getUserList() {
        Map valuesMap = new HashMap();

        System.out.println(restHostUrl);
        valuesMap.put("host", restHostUrl);

        valuesMap.get("host");
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUsers);

        System.out.println(req);

        return restTemplate.getForObject(req, ArrayList.class);
    }
}
