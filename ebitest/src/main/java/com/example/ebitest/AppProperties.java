package com.example.ebitest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ebiapp")
public class AppProperties {

    private String url;
    private String username;
    private int maxRecordLimit;

    public int getMaxRecordLimit() {
        return maxRecordLimit;
    }

    public void setMaxRecordLimit(int maxRecordLimit) {
        this.maxRecordLimit = maxRecordLimit;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}