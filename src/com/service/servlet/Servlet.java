package com.service.servlet;

import com.server.core.pojo.Request;
import com.server.core.pojo.Response;

public interface Servlet {
    void service(Request request, Response response) throws Exception;

//    void doGet(Request request, Response response);
//
//    void doPost(Request request, Response response);
}
