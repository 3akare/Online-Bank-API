package com.onlinebanking.bank.dto;

import lombok.*;

@Builder
public record ResponseDTO (String responseCode, String responseMessage, AccountInfoDTO accountInfo){};
