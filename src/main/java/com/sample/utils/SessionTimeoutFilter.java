package com.sample.utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionTimeoutFilter implements Filter {

    private static final long TIMEOUT_DURATION = 1 * 60 * 1000; // 10 minutes in milliseconds
    private static final String LOGIN_TIME_ATTRIBUTE = "loginTime";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            Long loginTime = (Long) session.getAttribute(LOGIN_TIME_ATTRIBUTE);
            if (loginTime != null && hasSessionTimedOut(loginTime)) {
                // Session has timed out, invalidate the session
                session.invalidate();
                // Redirect or return an error response as appropriate
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean hasSessionTimedOut(long loginTime) {
        long currentTime = System.currentTimeMillis();
        return currentTime - loginTime > TIMEOUT_DURATION;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
