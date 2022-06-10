package com.fudan.se.community.exception;

public class TokenFail extends RuntimeException {
    private static final long serialVersionUID = -60747539407108699L;
    public TokenFail(String message) {
        super(message);
    }
}
