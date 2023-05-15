package com.server.core.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

public class Request {
    private String requestInfo;

    private String requestMethod;
    private String url;

    private String queryStr;
    private String sessionId;

    private Map<String, List<String>> parameterMap;

    private final String CRLF = "\r\n";

    public Request(Socket clientSocket) throws IOException {

        this(clientSocket.getInputStream());
    }


    public Request(InputStream inputStream) throws IOException {


        byte[] bytes = new byte[1024 * 1024];
        int len = inputStream.read(bytes);
        parameterMap = new HashMap<String, List<String>>();
        requestInfo = new String(bytes, 0, len);
        parseRequestInfo();
    }


    public String getRequestInfo() {
        return requestInfo;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryStr() {
        return queryStr;
    }

    private void parseRequestInfo() {
        System.out.println("------parsing request-------");

        //1. get the request method
        requestMethod = requestInfo.substring(0, requestInfo.indexOf("/")).trim();
        System.out.println("method---> " + requestMethod);

        //2. get url
        int startIndex = this.requestInfo.indexOf("/") + 1;
        int endIndex = this.requestInfo.indexOf("HTTP/");
        this.url = requestInfo.substring(startIndex, endIndex).trim();

        int queryIndex = url.indexOf("?");

        if (queryIndex > 0) {
            String[] urlArray = url.split("\\?");

            url = urlArray[0].trim();
            queryStr = urlArray[1].trim();
        }

        System.out.println("url---> " + this.url);

        // get parameters
        if (requestMethod.equals("POST")) {
            String qStr = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
            if (!qStr.equals("")) {
                if (null == queryStr) {
                    queryStr = qStr;
                } else {
                    queryStr += "&" + qStr;
                }
            }

        }
        if (queryStr == null) {
            System.out.println("no query");
        } else {
            //convert to Map sid=123456&pwd=123456&others
            convertMap();
            System.out.println("query--->: " + queryStr);
        }

        //get cookie:
        String[] lines = requestInfo.split(CRLF);
        String cookie = null;
        for (String line : lines) {
            if (line.startsWith("Cookie: ")) {
                cookie = line.substring(8);
                break;
            }
        }
        if (cookie != null) {
            System.out.println("Cookie: " + cookie);
            int index = cookie.indexOf("session_id=");
            if(index != -1){
                String[] strings = cookie.substring(index).split("=");
                if(strings.length > 1){
                    sessionId = strings[1].trim();
                }
            }
        } else {
            System.out.println("No cookies found.");
        }



    }

    /**
     * put the queryStr into the parameter Map
     */
    private void convertMap() {
        //split by "&"
        String[] keyValues = queryStr.split("&");
        //split by "="
        for (String queryStr : keyValues) {
            String[] keyValue = queryStr.split("=");
            keyValue = Arrays.copyOf(keyValue, 2);

            String key = keyValue[0];
            String value = keyValue[1];
            if (!parameterMap.containsKey(key)) {
                parameterMap.put(key, new ArrayList<>());
            }
            parameterMap.get(key).add(value);
        }
    }

    /**
     * get multiple parameter values by key
     */
    public String[] getMultipleParameters(String key) {
        List<String> values = this.parameterMap.get(key);
        if (values == null || values.size() < 1) {
            return null;
        }
        return values.toArray(new String[0]);
    }

    /**
     * get single parameter value by key
     *
     * @return
     */
    public String getSingleParameter(String key) {
        String[] values = getMultipleParameters(key);
        return values == null ? null : values[0];
    }


    public String getSessionId(){
        return sessionId;
    }
}
