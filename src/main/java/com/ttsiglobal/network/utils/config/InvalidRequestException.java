package com.ttsiglobal.network.utils.config;

public class InvalidRequestException  extends Exception{
    private static final long serialVersionUID = 21021L;
    public InvalidRequestException(String message){
        super(message);
    }
}