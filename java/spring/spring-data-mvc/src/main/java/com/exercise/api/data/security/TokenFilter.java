package com.exercise.api.data.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TokenFilter extends OncePerRequestFilter {
    private final String TOKEN = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (isValid(httpServletRequest)) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Authorities.API);
            List<SimpleGrantedAuthority> authorities = Arrays.asList(authority);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("user2", null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else{
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public boolean isValid(HttpServletRequest httpServletRequest){
        String authenticationHeader = httpServletRequest.getHeader("Authorization");
        if (Objects.nonNull(authenticationHeader) && authenticationHeader.equals(TOKEN))
            return true;
        return false;
    }
}
