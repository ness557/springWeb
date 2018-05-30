package ness.security;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.simple.JSONObject;
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

        String token = restTemplate.getForObject(req, String.class);
        if (token != null)
            logger.info("Got token");
        else
            logger.info("Error");

        return token;
    }

    @Override
    public User getUser(String token) {
        logger.info("Trying to get user by token = " + token);

        Map valuesMap = new HashMap();

        valuesMap.put("host", properties.getProperty("rest.hosturl"));
        valuesMap.put("token", token);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getUser);

        JSONObject jsonUser = restTemplate.getForObject(req, JSONObject.class);

        if (jsonUser != null) {

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
