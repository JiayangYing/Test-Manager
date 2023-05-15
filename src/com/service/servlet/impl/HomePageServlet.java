package com.service.servlet.impl;

import com.server.core.Filter;
import com.server.core.pojo.Request;
import com.server.core.pojo.Response;
import com.service.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;

public class HomePageServlet implements Servlet {

    @Override
    public void service(Request request, Response response) throws Exception {
        // if session is valid
        if(Filter.isValid(request)){
            System.out.println("homepageServlet");
            doGet(request,response);
        }else {
            response.pushToBrowser(302,"http://localhost:8080/login");
        }
    }

    private void doGet(Request request, Response response) throws IOException {
        String fileName = "homepage.html";
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(fileName);
        response.println(new String(inputStream.readAllBytes()));

        System.out.println("push");
        response.pushToBrowser(200);
    }
}
