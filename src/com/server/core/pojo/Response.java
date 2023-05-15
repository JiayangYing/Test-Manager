package com.server.core.pojo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Optional;

public class Response {
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    private BufferedWriter bufferedWriter;

    // response header info
    private StringBuilder headerInfo;

    //body
    private StringBuilder content;
    private int len;

    private Response() {
        content = new StringBuilder();
        headerInfo = new StringBuilder();
        len = 0;
    }

    public Response(Socket clientSocket) {
        this();
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("failed to create a bufferedWriter");
            throw new RuntimeException(e);
        }
    }

    public Response print(String info) {
        content.append(info);
        len += info.getBytes().length;
        return this;
    }

    public Response println(String info) {
        content.append(info).append(CRLF);
        len += (info + CRLF).getBytes().length;
        return this;
    }

    private void createHeaderInfo(int status, Optional<String> link, Optional<String> cookie) {
        // Response Line
        headerInfo.append("HTTP/1.1").append(BLANK);
        headerInfo.append(status).append(BLANK);
        switch (status) {
            case 200:
                headerInfo.append("OK");
                break;
            case 302:
                headerInfo.append("Found");
                break;
            case 404:
                headerInfo.append("NOT FOUND");
                break;
            case 505:
                headerInfo.append("SERVER ERROR");
                break;
        }
        headerInfo.append(CRLF);

        // Response header
        link.ifPresent(l -> headerInfo.append("Location:").append(BLANK).append(l).append(CRLF));
        cookie.ifPresent(c -> headerInfo.append("Set-Cookie:").append(BLANK).append(c).append(CRLF));
        headerInfo.append("Date:").append(new Date()).append(CRLF);
        headerInfo.append("Server:").append("TM Server/0.0.1;charset=UTF-8").append(CRLF);
        headerInfo.append("Content-Type: ").append("text/html").append(CRLF);
        headerInfo.append("Content-Length: ").append(len).append(CRLF);

        headerInfo.append(CRLF);
    }

    public void pushToBrowser(int status) throws IOException {
        pushToBrowser(status, Optional.empty(), Optional.empty());
    }

    public void pushToBrowser(int status, String link) throws IOException {
        pushToBrowser(status, Optional.of(link), Optional.empty());
    }

    public void pushToBrowser(int status, String link, String cookie) throws IOException {
        pushToBrowser(status, Optional.of(link), Optional.of(cookie));
    }

    private void pushToBrowser(int status, Optional<String> link, Optional<String> cookie) throws IOException {
        createHeaderInfo(status, link, cookie);
        bufferedWriter.append(headerInfo);
        bufferedWriter.append(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
