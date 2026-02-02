package com.example.demo.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain )
        throws ServletException, IOException
    {
        String path=request.getRequestURI();
        if(path.equals("/api/auth/login")||path.startsWith("/swagger-ui")||
                path.startsWith("/v3/api-docs")
        )
        {
            filterChain.doFilter(request,response);
            return;
        }
        String token =null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("AUTH_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null){

            UsernamePasswordAuthenticationToken auth = new  UsernamePasswordAuthenticationToken("use", null, Collections.emptyList() );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("unauthorized");
            return;}

        filterChain.doFilter(request, response);
    }

}
