package com.pseandroid2.dailydataserver.requestBodyLogic;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * #TODO official Test
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = ("/*"))
public class ConstantBodyFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        BodyServletRequestWrapper bodyRequest = new BodyServletRequestWrapper(request);
        filterChain.doFilter(bodyRequest,response);
    }
}
