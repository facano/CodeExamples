package com.exercise.api.data.security;

import com.exercise.api.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Configurable
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("apiUser")
    private UserDetails userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (isValid(httpServletRequest)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else{
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public boolean isValid(HttpServletRequest httpServletRequest){
        String token = ((User)userDetails).getToken();

        String authenticationHeader = httpServletRequest.getHeader("Authorization");
        if (Objects.nonNull(authenticationHeader) && authenticationHeader.equals(token))
            return true;
        return false;
    }
}
