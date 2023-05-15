package com.server.core.pojo;

import java.text.SimpleDateFormat;
import java.util.*;

public class Cookie {

    //  "HTTP/1.1 200 OK\r\n" +
    //                                 "Content-Type: text/html\r\n" +
    //                                 "Set-Cookie: session_id=12345; " +
    //                                 "expires=" + getCookieExpiresDate() + "; " +
    //                                 "path=/; domain=localhost; HttpOnly\r\n";


    public static String getCookie(String sessionId) {
        StringBuilder cookie = new StringBuilder();
        cookie.append("session_id=").append(sessionId).append(";");
        cookie.append("expires=").append(getCookieExpiresDate(1)).append("; "); // expire in one day
        cookie.append("path=/").append("; ");
        cookie.append("domain=localhost").append("; ");
        cookie.append("HttpOnly");
        return cookie.toString();
    }

    private static String getCookieExpiresDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Australia/Perth")); // set to perth TimeZone
        return sdf.format(cal.getTime());
    }
}
