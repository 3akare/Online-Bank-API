package com.onlinebanking.bank.dto;

import lombok.Builder;

@Builder
public record GenerateStatementDTO(String accountNumber, String startDate, String endDate) {}
