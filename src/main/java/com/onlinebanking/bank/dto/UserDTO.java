package com.onlinebanking.bank.dto;

import lombok.*;

@Builder
public record UserDTO (String firstName,
                       String lastName,
                       String otherName,
                       String gender,
                       String address,
                       String stateOfOrigin,
                       String dateOfBirth,
                       String email,
                       String phoneNumber,
                       String alternativePhoneNumber){};
