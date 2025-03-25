package com.onlinebanking.bank.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
public record AccountInfoDTO (String accountName, String accountNumber, BigDecimal accountBalance){}
