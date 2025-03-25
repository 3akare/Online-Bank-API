package com.onlinebanking.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
public record CreditDebitDTO (String accountNumber, BigDecimal amount) {}
