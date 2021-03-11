package com.example.bank.Exception;

import java.util.UUID;

public class AccountNotFoundException extends Throwable {
    public AccountNotFoundException(UUID id) {
        super("Unable to find accoount id = [" + id + "]");
    }
}