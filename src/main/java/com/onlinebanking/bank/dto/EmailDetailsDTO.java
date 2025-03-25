package com.onlinebanking.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record EmailDetailsDTO ( String recipient, String messageBody, String subject, String attachment){}
