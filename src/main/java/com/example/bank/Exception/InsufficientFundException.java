package com.example.bank.Exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundException extends Throwable {
    public InsufficientFundException(UUID accountId, BigDecimal withdrawAmount) {
        super("Insufficient Fund: Cannot withdraw " + withdrawAmount +
                " from account number [" + accountId.toString() + "]");
    }

}
