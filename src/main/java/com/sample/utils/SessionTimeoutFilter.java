package com.sample.utils;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.Instant;
@WebFilter(filterName = "SessionTimeoutFilter", urlPatterns = {"/*"})
public class SessionTimeoutFilter implements Filter {
    private static final long TIMEOUT_DURATION = 1 * 60 * 1000; // 10 minutes in milliseconds
    private static final String LOGIN_TIME_ATTRIBUTE = "loginTime";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if any
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
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
        long currentTime = Instant.now().toEpochMilli();
        return currentTime - loginTime > TIMEOUT_DURATION;
    }

    @Override
    public void destroy() {
        // Cleanup code, if any
    }
}
