package com.onlinebanking.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
public record TransferDTO ( String senderAccountNumber, String receiverAccountNumber, BigDecimal amount){}
