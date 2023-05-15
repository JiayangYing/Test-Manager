package com.server.core;

import com.server.core.pojo.Request;
import com.server.core.pojo.Response;
import com.service.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Dispatcher implements Runnable{
    private Socket clientSocket;
    private Request request;
    private Response response;
    public Dispatcher(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            request = new Request(clientSocket);
            response = new Response(clientSocket);
        } catch (Exception e) {
            release();
            System.out.println("failed to get request");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            Servlet servlet = WebApp.getServletByUrl(request.getUrl());
            if (servlet != null) {
                servlet.service(request, response);

            } else {
                InputStream inputStream = Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream("error.html");

                response.println(new String(inputStream.readAllBytes()));
                response.pushToBrowser(404);

            }
        } catch (Exception e) {
            try {

                response.pushToBrowser(500);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        release();
        System.out.println("release thread\n\n");

    }

    private void release(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("failed to close clientSocket");
            throw new RuntimeException(e);
        }
    }
}
