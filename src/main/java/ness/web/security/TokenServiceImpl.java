package ness.web.security;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

@Service("theTokenService")
public class TokenServiceImpl implements TokenService {

    @Resource(name = "myProperties")
    private Properties properties;

    private static final String getUser = "${host}/token/getUser?token=${token}";
    private static final String getToken = "${host}/token/getToken?username=${username}&password=${password}";

    private RestTemplate restTemplate;
    private Logger logger = Logger.getLogger(getClass().getName());

    public TokenServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public String getToken(String username, String password) throws AuthenticationException {
        logger.info("Trying to get token by username = "
                + username + " and password = " + password);

        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("username", username);
        valuesMap.put("password", password);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getToken);

        ResponseEntity responseEntity= restTemplate.getForEntity(req, String.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Got token");
            return (String) responseEntity.getBody();
        }
        else
            logger.info("Error");

        return null;
    }

    @Override
    public User getUser(String token) {
        logger.info("Trying to get user by token = " + token);

        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("token", token);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUser);

        ResponseEntity responseEntity = restTemplate.getForEntity(req, JSONObject.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            JSONObject jsonUser = (JSONObject) responseEntity.getBody();

            User user = new User((String) jsonUser.get("username"),
                    (String) jsonUser.get("password"),
                    true, true, true, true,
                    new ArrayList<>());

            logger.info("Got user = " + user.toString());
            return user;
        } else
            logger.info("Error");

        return null;
    }
}
