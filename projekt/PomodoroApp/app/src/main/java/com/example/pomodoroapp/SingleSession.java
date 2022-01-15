package com.example.pomodoroapp;

public class SingleSession {
    private String Date;
    private String sessionLength;
    private String breakLength;

    public SingleSession(String date, String sessionLen, String breakLen) {
        Date = date;
        sessionLength = sessionLen;
        breakLength = breakLen;
    }

    public String getDate() {
        return Date;
    }

    public String getSessionLength() {
        return sessionLength;
    }

    public String getBreakLength() {
        return breakLength;
    }
}
