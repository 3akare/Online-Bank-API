package com.onlinebanking.bank.dto;

import lombok.Builder;

@Builder
public record EnquiryRequestDTO(String accountNumber) {
}
