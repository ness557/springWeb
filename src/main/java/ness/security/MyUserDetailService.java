package ness.security;

import ness.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("theUserDetailsService")
public class MyUserDetailService implements UserDetailsService {

    private UserService service;

    @Autowired
    public MyUserDetailService(UserService service) {
        this.service = service;
        System.out.println("\n\n\n\n\n\nMyUserDetailService constructor\n\n\n\n\n\n\n");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("\n\n\n\n\n\nMyUserDetailService loadUserByUsername\n\n\n\n\n\n\n");
        JSONObject user = new JSONObject(service.getUserByUsername(username).toMap());

        if (user.isEmpty())
            throw new UsernameNotFoundException("Username: " + username);

        List<GrantedAuthority> authorities = buildUserAuthorities((String) user.get("roles"));

        return buildUserForAuth(user, authorities);
    }

    private UserDetails buildUserForAuth(JSONObject user, List<GrantedAuthority> authorities) {

        return new User((String) user.get("username"), (String) user.get("password"),
                true, true, true, true, authorities);

    }

    private List<GrantedAuthority> buildUserAuthorities(String roles) {

        return Stream.of(roles.split(","))
                .map(n -> new SimpleGrantedAuthority(StringUtils.deleteWhitespace(n)))
                .collect(Collectors.toList());
    }
}
