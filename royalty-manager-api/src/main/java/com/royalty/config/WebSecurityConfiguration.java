package com.royalty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] ALLOWED_USERS = { "user" };

    private static final String PASSWORD = "user";

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {

            if (Arrays.asList(ALLOWED_USERS).contains(username)) {
                return new User(username, PASSWORD, true, true, true, true,
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
            } else {
                throw new UsernameNotFoundException("The user is not allowed: '" + username + "'");
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);
    }

    public class WebSecurityCorsFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
            chain.doFilter(request, res);
        }

        @Override
        public void destroy() {
        }

    }
}

