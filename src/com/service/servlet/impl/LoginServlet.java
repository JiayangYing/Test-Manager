package com.service.servlet.impl;

import com.server.core.Filter;
import com.server.core.StudentInfoHandler;
import com.server.core.pojo.*;
import com.service.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.UUID;


public class LoginServlet implements Servlet {

    @Override
    public void service(Request request, Response response) throws Exception {

        // if sessionId is valid, log in without sid and pwd
        if(Filter.isValid(request))
        {
            response.pushToBrowser(302,"http://localhost:8080/homepage");
        }else {


            String requestMethod = request.getRequestMethod();
            System.out.println("requestMethod--> " + requestMethod);
            if (requestMethod.equals("GET")) {
                doGet(request, response);
            } else if (requestMethod.equals("POST")) {
                doPost(request, response);

            }
        }

        System.out.println("end loginServlet");
    }

    private void doGet(Request request, Response response) throws IOException {

        String fileName = "login.html";
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(fileName);
        response.println(new String(inputStream.readAllBytes()));
        response.pushToBrowser(200);

    }

    /**
     * verify student identity
     * @param request
     * @param response
     */
    private void doPost(Request request, Response response) throws IOException {
        // get student info
        int sid = Integer.parseInt(request.getSingleParameter("sid"));
        String pwd = request.getSingleParameter("pwd");
        Student student = StudentInfoHandler.getInstance().getStudentBySid(sid);

        // check identity
        if(sid == student.getSid() && pwd.equals(student.getPwd())){
            // add a new session into SessionMap
            String sessionId  = UUID.randomUUID().toString();
            StudentInfoHandler.getInstance().addSession(new Session(sessionId, sid, 30));

            // create a cookie and push to browser
            String cookie = Cookie.getCookie(sessionId);
            response.pushToBrowser(302,"http://localhost:8080/homepage",cookie);
        }else {
            String fileName = "invalidLogin.html";
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(fileName);
            response.println(new String(inputStream.readAllBytes()));
            response.pushToBrowser(200);
        }
    }
}
