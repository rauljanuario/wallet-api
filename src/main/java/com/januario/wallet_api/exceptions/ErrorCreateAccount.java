package com.januario.wallet_api.exceptions;

public class ErrorCreateAccount extends RuntimeException {
    public ErrorCreateAccount(String message) {
        super(message);
    }
}
