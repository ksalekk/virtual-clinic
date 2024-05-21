package com.jsalek.pw.virtualclinic.security.authorization;

import com.jsalek.pw.virtualclinic.security.user.Principal;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BasicAuthFilter implements Filter {

    private final AuthGuard authGuard;

    public BasicAuthFilter(AuthGuard authGuard) {
        this.authGuard = authGuard;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession httpSession = httpRequest.getSession(false);
        String endpoint = httpRequest.getServletPath();


        if(HttpMethod.valueOf(httpRequest.getMethod()) == HttpMethod.OPTIONS) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if(this.authGuard.isEndpointWhitelisted(endpoint)) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else if(httpSession == null || httpSession.getAttribute("principal") == null) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);

        } else {
            Principal principal = (Principal) httpSession.getAttribute("principal");
            HttpMethod method = HttpMethod.valueOf(httpRequest.getMethod());

            if(this.authGuard.hasProperRole(method, endpoint, principal.role())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
