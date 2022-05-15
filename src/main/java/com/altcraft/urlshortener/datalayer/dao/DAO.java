package com.altcraft.urlshortener.datalayer.dao;

import com.altcraft.urlshortener.datalayer.LinkInfo;

public interface DAO {
    void saveLink(LinkInfo linkInfo);
    String findHash(String originalLink);
    String findOriginalLink(String hash);
}
