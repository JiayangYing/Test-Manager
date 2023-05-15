package com.server.core.pojo;

import java.io.Serializable;
import java.util.Date;


public class Session implements Serializable {
    private String sessionId;
    private int sid;
    private Date expirationTime;

    /**
     * create a session
     * @param sessionId
     * @param sid
     * @param minutes the minutes in which the session will expire
     */
    public Session(String sessionId, int sid, int minutes) {
        this.sessionId = sessionId;
        this.sid = sid;
        setExpirationTime(minutes);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isExpired() {
        return expirationTime.before(new Date());
    }

    public void setExpirationTime(int minute) {
        long milliseconds = minute * 60 * 1000L;
        this.expirationTime = new Date(System.currentTimeMillis() + milliseconds);
    }
}
