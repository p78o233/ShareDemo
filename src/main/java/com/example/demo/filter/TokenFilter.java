package com.example.demo.filter;
import com.example.demo.mapper.FilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public class TokenFilter implements Filter{
    @Autowired
    private FilterMapper filterMapper;
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/shares/stock/login")));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------------test token filter doFilter --------------");
//        if(servletRequest instanceof HttpServletRequest) {
//            HttpServletRequest req = (HttpServletRequest) servletRequest;
//            String token = req.getHeader("token");
//            System.out.println("token = " + token);
//            if(token != null) {
//                System.out.println("通过了");
//            }else {
//                System.out.println("没通过");
//                return;
//            }
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (allowedPath) {
//            这里是不需要处理的url进入的方法
            filterChain.doFilter(request, response);
        }
        else {
//            这里是需要处理的url进入的方法

        }
    }

    @Override
    public void destroy() {

    }
}
