package com.altcraft.urlshortener.datalayer;

public class LinkInfo {
    private String originalURL;
    private String shortedURL;
    private String timeCreated;

    public LinkInfo(String originalURL, String shortedURL, String timeCreated) {
        this.originalURL = originalURL;
        this.shortedURL = shortedURL;
        this.timeCreated = timeCreated;
    }

    public LinkInfo(String originalURL, String hashURL) {
        this.originalURL = originalURL;
        this.shortedURL = hashURL;
    }

    public LinkInfo() {
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getShortedURL() {
        return shortedURL;
    }

    public void setShortedURL(String shortedURL) {
        this.shortedURL = shortedURL;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
}
