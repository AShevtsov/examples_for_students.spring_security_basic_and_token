package com.students.security.filters;

import com.students.model.User;
import com.students.security.jwt.JwtToken;
import com.students.security.jwt.JwtTokenProvider;
import com.students.security.services.UserSpringDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final UserSpringDetailsService springDetailsService;

    public TokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserSpringDetailsService springDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.springDetailsService = springDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        LOG.debug("[");

        JwtToken accessToken = jwtTokenProvider.resolveToken(httpServletRequest);
        LOG.trace("accessToken : {}", accessToken);

        if (accessToken != null) {
            // TODO: 21.11.2019 add check by username + token
            UserDetails userDetails = springDetailsService.loadUserByUsername(accessToken.getUsername());
            LOG.trace("userDetails : {}", userDetails);

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        LOG.debug("]");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
