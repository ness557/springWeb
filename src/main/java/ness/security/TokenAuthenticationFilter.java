package ness.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Resource(name = "myProperties")
    private Properties properties;

    @Autowired
    @Qualifier("theTokenService")
    private TokenService tokenService;

    @Autowired
    @Qualifier("theUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("\n\n\n\n\n\n TokenAuthenticationFilter doFilter\n\n\n\n\n\n\n");


        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        final String accessToken = httpRequest.getHeader(properties.getProperty("security.header.name"));

        if (accessToken != null) {
            User userFromToken = tokenService.getUser(accessToken);
            if (userFromToken == null) {

                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Error while parsing token");
                return;
            }

            UserDetails userFromDB = userDetailsService.loadUserByUsername(userFromToken.getUsername());
            if (userFromDB == null) {

                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "User with username from token not found");
                return;
            }

            if(!userFromDB.getPassword().equals(userFromToken.getPassword())){

                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Bad credentials in token");
                return;
            }

            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userFromDB, userFromDB, userFromDB.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
