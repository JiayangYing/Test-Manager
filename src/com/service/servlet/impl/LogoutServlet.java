package com.service.servlet.impl;

import com.server.core.pojo.Request;
import com.server.core.pojo.Response;
import com.service.servlet.Servlet;

import java.io.InputStream;

public class LogoutServlet implements Servlet {
    @Override
    public void service(Request request, Response response) throws Exception {
        String fileName = "login.html";
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(fileName);
        response.println(new String(inputStream.readAllBytes()));
        response.pushToBrowser(200);
    }
}
