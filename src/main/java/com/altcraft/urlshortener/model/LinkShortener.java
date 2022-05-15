package com.altcraft.urlshortener.model;

import org.hashids.Hashids;

public class LinkShortener {
    private static final int START = 0;
    private static final int END = 9;


    public String reduceLinkHashids(String link) {
        Hashids hashids = new Hashids(link);
        String hashedLink = hashids.encode(System.currentTimeMillis());

        return hashedLink.substring(START, END);
    }
}
