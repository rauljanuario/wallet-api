package com.januario.wallet_api.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorMessage(HttpStatus status, String message) {
}
