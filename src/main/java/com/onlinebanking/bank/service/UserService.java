package com.onlinebanking.bank.service;

import com.onlinebanking.bank.dto.*;
import jakarta.mail.MessagingException;

public interface UserService {
    ResponseDTO createAccount(UserDTO userDTO) throws MessagingException;
    ResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequestDTO);
    ResponseDTO accountNameEnquiry(EnquiryRequestDTO enquiryRequestDTO);
    ResponseDTO creditAccount(CreditDebitDTO creditDebitDTO) throws MessagingException;
    ResponseDTO debitAccount(CreditDebitDTO creditDebitDTO) throws MessagingException;
    ResponseDTO transfer(TransferDTO transferDTO) throws MessagingException;
}
