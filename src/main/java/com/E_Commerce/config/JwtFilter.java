package com.E_Commerce.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
    throws ServletException,IOException {
        String auth=request.getHeader("Authorization");
        if(request.getMethod().equals("POST")&&request.getRequestURI().contains("/api/products")){
            if(auth==null||!auth.startsWith("Bearer ")){
                response.setStatus(401);
                response.getWriter().write("JWT Token Missing");
                return;
            }
        }
        chain.doFilter(request,response);
    }

}