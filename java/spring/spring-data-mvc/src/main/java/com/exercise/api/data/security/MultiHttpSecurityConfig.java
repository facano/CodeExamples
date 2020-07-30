package com.exercise.api.data.security;

import com.exercise.api.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {
    @Autowired
    private UserService userService;

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private TokenFilter tokenFilter;

        @Autowired
        protected PasswordEncoder passwordEncoder;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .anyRequest()
                        .hasAuthority(Authorities.API)
                    .and()
                    .csrf()
                        .disable();
        }
    }

    @Configuration
    @Order(2)
    public static class FrontendWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("frontUser")
        private UserDetails userDetails;

        @Autowired
        protected PasswordEncoder passwordEncoder;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .anyRequest()
                    .hasAuthority(Authorities.FRONTEND)
                    .and()
                .formLogin()
                    .loginPage("/login")
                        .permitAll()
                .and()
                .logout()
                    .permitAll();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(new InMemoryUserDetailsManager(userDetails))
                    .passwordEncoder(passwordEncoder);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("frontUser")
    public UserDetails frontUserDetails(){
        return userService.findByAuthority(Authorities.FRONTEND);
    }

    @Bean("apiUser")
    public UserDetails apiUserDetails(){
        return userService.findByAuthority(Authorities.API);
    }
}
