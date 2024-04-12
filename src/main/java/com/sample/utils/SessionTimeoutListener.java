package com.sample.utils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionTimeoutListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute("loginTime", System.currentTimeMillis());
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

    }
}
