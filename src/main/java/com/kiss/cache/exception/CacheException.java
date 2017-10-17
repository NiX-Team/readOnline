package com.kiss.cache.exception;

public class CacheException extends Exception{
    private String msg;
    public CacheException(){}
    public CacheException(String msg){
        this.msg = msg;
    }
}
