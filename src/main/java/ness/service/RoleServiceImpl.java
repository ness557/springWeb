package ness.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ness.model.Role;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource(name = "myProperties")
    private Properties properties;

    private static final String getRole = "${host}/roles?id=${id}";
    private static final String getRoles = "${host}/roles";
    private static final String addRole = "${host}/roles";
    private static final String editRole = "${host}/roles";
    private static final String removeRole = "${host}/roles?id=${id}";

    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(getClass().getName());

    public RoleServiceImpl() {

        this.restTemplate = new RestTemplate();
    }

    @Override
    public void addRole(Role role) {
        logger.info("Executing PUT method to add role " + role);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(addRole);
        restTemplate.put(req, role);

    }

    @Override
    public void updateRole(Role role) {
        logger.info("Executing POST method to update role " + role);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(editRole);

        restTemplate.postForEntity(req, role, Role.class);
    }

    @Override
    public void removeRole(Role role) {
        logger.info("Executing DELETE method to delete role " + role);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("id", role.getId());

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeRole);

        restTemplate.delete(req);
    }

    @Override
    public void removeRole(int id) {
        logger.info("Executing DELETE method to delete role by id = " + id);

        Map valuesMap = new HashMap();
        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeRole);

        restTemplate.delete(req);
    }

    @Override
    public Role getRoleById(int id) {
        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getRole);

        return restTemplate.getForObject(req, Role.class);
    }

    @Override
    public List<Role> getRoles() {
        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getRoles);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode roles = restTemplate.getForObject(req, JsonNode.class);

        List<Role> list = null;
        try {
            list = mapper.readValue(
                    mapper.treeAsTokens(roles), new TypeReference<List<Role>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Role findRoleByName(String name) {
        logger.info("Trying to find role by name = " + name);

        List<Role> roles = getRoles();
        logger.warning("\n\n\n\n");


        for (Role role : roles) {
            if (role.getName().equals(name)) {
                logger.info("Role found: " + role);
                return role;
            }
        }
        logger.info("Role NOT found");

        return null;
    }
}
