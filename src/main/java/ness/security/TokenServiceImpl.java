package ness.security;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
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
        if(token != null)
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

        User user = restTemplate.getForObject(req, User.class);
        if(user != null)
            logger.info("Got user = " + user.toString());
        else
            logger.info("Error");

        return user;
    }
}
