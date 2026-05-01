package com.example.ebankingbackend.exception;

public class BankAccountNoFoundException extends Exception {
    public BankAccountNoFoundException(String message) {
        super(message);
    }
}
