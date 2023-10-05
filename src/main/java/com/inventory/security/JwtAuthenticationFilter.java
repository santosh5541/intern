package com.inventory.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JWTHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JWTHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get token from header
        String requestToken = request.getHeader("Authorization");
        logger.info("Received token: {}", requestToken);
        String username = null;
        String jwtToken = null;

        try {
            if (requestToken != null && requestToken.trim().startsWith("Bearer")) {
                // Get actual token (trim any leading/trailing whitespace)
                jwtToken = requestToken.substring(7).trim();
                username = jwtHelper.getUsername(jwtToken);
            }
        } catch (ExpiredJwtException e) {
            logger.error("Invalid token: JWT token expired", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid token: Malformed JWT token", e);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid token: Unable to get token", e);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validate the token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtHelper.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                logger.error("Invalid token: Token validation failed");
                // Consider sending an error response to the client here
            }
        } else {
            logger.error("Invalid token: Token does not start with 'Bearer'");
            // Consider sending an error response to the client here
        }

        filterChain.doFilter(request, response);
    }
}
