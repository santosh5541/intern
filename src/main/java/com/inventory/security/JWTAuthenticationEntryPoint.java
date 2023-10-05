package com.inventory.security;

import com.fasterxml.jackson.databind.ObjectMapper; // Import ObjectMapper for JSON conversion
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json"); // Set content type to JSON

        // Create a JSON response
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Unauthorized Access");
        errorResponse.put("error", "Unauthorized");

        // Convert the errorResponse map to JSON and write it to the response
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
