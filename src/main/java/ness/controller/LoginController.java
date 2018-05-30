package ness.controller;

import ness.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import java.util.Properties;
import java.util.logging.Logger;

@RestController
public class LoginController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Resource(name = "myProperties")
    private Properties properties;

    private TokenService service;

    @Autowired
    public LoginController(TokenService tokenService){
        this.service = tokenService;
    }

    @RequestMapping("/login")
    public ResponseEntity login(@RequestParam("username") String username,
                                @RequestParam("password") String password){

        try {
            String result = service.getToken(username, password);

            logger.info("Got token: " + result);
            return ResponseEntity.ok().header(properties.getProperty("security.header.name"), result).body(result);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
