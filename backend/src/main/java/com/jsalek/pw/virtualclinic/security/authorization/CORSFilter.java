package com.jsalek.pw.virtualclinic.security.authorization;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CORSFilter implements Filter {
    private String allowHeaders;
    private String allowOrigin;
    private String allowCredentials;
    private String allowMethods;


    @Autowired
    public void configCors(Environment env) {
        this.allowHeaders = env.getProperty("custom.cors.allow-headers");
        this.allowOrigin = env.getProperty("custom.cors.allow-origin");
        this.allowCredentials = env.getProperty("custom.cors.allow-credentials");
        this.allowMethods = env.getProperty("custom.cors.allow-methods");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        httpResponse.addHeader("Access-Control-Allow-Headers", allowHeaders);
        httpResponse.addHeader("Access-Control-Allow-Origin", allowOrigin);
        httpResponse.addHeader("Access-Control-Allow-Credentials", allowCredentials);
        httpResponse.addHeader("Access-Control-Allow-Methods", allowMethods);

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
