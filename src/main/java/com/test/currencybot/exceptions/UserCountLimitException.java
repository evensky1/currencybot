package com.test.currencybot.exceptions;

public class UserCountLimitException extends RuntimeException {

    public UserCountLimitException(String s) {
        super(s);
    }
}
