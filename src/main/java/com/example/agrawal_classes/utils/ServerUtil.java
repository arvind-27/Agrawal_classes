package com.example.agrawal_classes.utils;

import org.springframework.stereotype.Component;

@Component
public class ServerUtil {
    public String getHostAddressAndPort() {
        String hostname = System.getenv("SERVER_HOSTNAME");
        if (hostname == null) {
            return "http://127.0.0.1:8080";
        } else {
            return hostname;
        }
    }
}
