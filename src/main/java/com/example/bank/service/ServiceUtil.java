package com.example.bank.service;

import java.util.UUID;

public class ServiceUtil {
	 public static UUID formatUuid(String accountId) {
	        accountId = accountId.replace("-", "");
	        String formatted = String.format(
	                accountId.substring(0, 8) + "-" +
	                        accountId.substring(8, 12) + "-" +
	                        accountId.substring(12, 16) + "-" +
	                        accountId.substring(16, 20) + "-" +
	                        accountId.substring(20, 32)
	        );
	        return UUID.fromString(formatted);
	    }
}
