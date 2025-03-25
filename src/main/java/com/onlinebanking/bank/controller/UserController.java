package com.onlinebanking.bank.controller;

import com.onlinebanking.bank.dto.*;
import com.onlinebanking.bank.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/createAccount")
    public ResponseDTO createAccount(@RequestBody  UserDTO userDTO) throws MessagingException {
        return userServiceImpl.createAccount(userDTO);
    }

    @PostMapping("/creditAccount")
    public ResponseDTO creditAccount(@RequestBody CreditDebitDTO creditDebitDTO) throws MessagingException {
        return userServiceImpl.creditAccount(creditDebitDTO);
    }

    @PostMapping("/debitAccount")
    public ResponseDTO debitAccount(@RequestBody CreditDebitDTO creditDebitDTO) throws MessagingException {
        return userServiceImpl.debitAccount(creditDebitDTO);
    }

    @PostMapping("/transfer")
    public ResponseDTO transfer(@RequestBody TransferDTO transferDTO) throws MessagingException {
        return  userServiceImpl.transfer(transferDTO);
    }

    @GetMapping("/balanceEnquiry")
    public ResponseDTO balanceEnquiry(@RequestBody EnquiryRequestDTO enquiryRequestDTO){
        return userServiceImpl.balanceEnquiry(enquiryRequestDTO);
    }

    @GetMapping("/accountNameEnquiry")
    public ResponseDTO accountNameEnquiry(@RequestBody EnquiryRequestDTO enquiryRequestDTO){
        return userServiceImpl.accountNameEnquiry(enquiryRequestDTO);
    }
}
