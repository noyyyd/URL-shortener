package com.altcraft.urlshortener.datalayer.connectionpool;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }
}
