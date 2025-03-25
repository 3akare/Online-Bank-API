package com.onlinebanking.bank.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {
    public static final String ACCOUNT_ALREADY_EXISTS_CODE = "001";
    public static final String ACCOUNT_ALREADY_EXISTS_MESSAGE = "This user already exists";
    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account successfully created!";
    public static final String ACCOUNT_DOES_NOT_EXISTS_CODE = "003";
    public static final String ACCOUNT_DOES_NOT_EXISTS_MESSAGE = "This account does not exist";
    public static final String ACCOUNT_EXISTS_CODE = "004";
    public static final String ACCOUNT_EXISTS_MESSAGE = "User found";
    public static final String ACCOUNT_CREDITED_SUCCESS_CODE = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account credited";
    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "006";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account debited";
    public static final String ACCOUNT_INSUFFICIENT_BALANCE_CODE = "007";
    public static final String ACCOUNT_INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance";
    public static final String ACCOUNT_TRANSFER_FAILED_CODE = "008";
    public static final String ACCOUNT_TRANSFER_INVALID_SENDER_ACCOUNT_NUMBER = "Invalid account number (sender)";
    public static final String ACCOUNT_TRANSFER_INVALID_RECEIVER_ACCOUNT_NUMBER = "Invalid account number (receiver)";
    public static final String ACCOUNT_TRANSFER_SUCCESS_CODE = "009";
    public static final String ACCOUNT_TRANSFER_SUCCESS_MESSAGE = "Transfer success";
    public static String generateAccountNumber(){
        /* [current year]+[0-6]*6 */
        Year currentYear = Year.now();

        /* Generate random number */
        Random random = new Random();
        int origin = 100000;
        int bound = 999999;
        int randomNumber = random.nextInt(origin,bound);

        /* Convert Year, and random number to String */
        String accountNumber = currentYear + "";
        accountNumber += randomNumber + "";
        return accountNumber;
    }
}
