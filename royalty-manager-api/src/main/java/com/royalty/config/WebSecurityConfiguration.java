package com.royalty.config;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration

class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] ALLOWED_USERS = {"user1", "user2"};
    private static final String PASSWORD = "password";

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {

            if(Arrays.asList(ALLOWED_USERS).contains(username)) {
                return new User(username, PASSWORD, true, true, true, true,
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
            } else {
                throw new UsernameNotFoundException("The user is not allowed: '" + username + "'");
            }
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().anonymous().disable()
                .exceptionHandling().authenticationEntryPoint(new BasicAuthenticationEntryPoint() {
            @Override
            public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException)
                    throws IOException, ServletException {
                if(HttpMethod.OPTIONS.matches(request.getMethod())){
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD));
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                }else{
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                }
            }
        });
    }

}