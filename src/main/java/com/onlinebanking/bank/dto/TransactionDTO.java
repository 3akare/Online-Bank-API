package com.onlinebanking.bank.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;


@Builder
public record
TransactionDTO( UUID transactionId,String transactionType, BigDecimal amount,String accountNumber, String status){}
