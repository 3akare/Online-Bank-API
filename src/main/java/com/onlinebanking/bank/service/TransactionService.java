package com.onlinebanking.bank.service;

import com.onlinebanking.bank.dto.TransactionDTO;

public interface TransactionService {
    void saveTransaction(TransactionDTO transactionDTO);
}
