package com.onlinebanking.bank.service.impl;

import com.onlinebanking.bank.dto.*;
import com.onlinebanking.bank.entity.User;
import com.onlinebanking.bank.repository.UserRepository;
import com.onlinebanking.bank.service.UserService;
import com.onlinebanking.bank.utils.AccountUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final TransactionServiceImpl transactionServiceImpl;

    @Override
    /* Creating an account - Saving a new user into Database */
    public ResponseDTO createAccount(UserDTO userDTO) throws MessagingException {
        /* check if user already exists */
        if (userRepository.existsByEmail(userDTO.email())){
            return ResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_ALREADY_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_ALREADY_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = User.builder()
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .otherName(userDTO.otherName())
                .gender(userDTO.gender())
                .address(userDTO.address())
                .stateOfOrigin(userDTO.stateOfOrigin())
                .dateOfBirth(userDTO.dateOfBirth())
                .email(userDTO.email())
                .phoneNumber(userDTO.phoneNumber())
                .alternativePhoneNumber(userDTO.alternativePhoneNumber())
                .status("ACTIVE")
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
        .build();

        User savedUser  = userRepository.save(user);
        AccountInfoDTO accountInfo = AccountInfoDTO.builder()
                .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                .accountNumber(savedUser.getAccountNumber())
                .accountBalance(savedUser.getAccountBalance())
                .build();

        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO
                .builder()
                .recipient(savedUser.getEmail())
                .subject("3ank: Account Creation Success")
                .build();

        /* Create context for templating */
        Context emailContext = getEmailContext(savedUser, "3ank: Account Creation Success", "Your 3ank account has been successfully created.");

        /* Send Email Alert */
        emailServiceImpl.sendEmailAlert(emailDetailsDTO, emailContext,"AccountMessage");

        return ResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(accountInfo)
                .build();
    }

    @Override
    public ResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequestDTO) {
        /* check if the account number exists */
        if (!userRepository.existsByAccountNumber(enquiryRequestDTO.accountNumber())){
            return ResponseDTO.builder()
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .accountInfo(null)
                    .build();
        }
        User user = userRepository.findByAccountNumber(enquiryRequestDTO.accountNumber());
        AccountInfoDTO accountInfo = AccountInfoDTO.builder()
                .accountName(user.getFirstName() + " " + user.getLastName())
                .accountBalance(user.getAccountBalance())
                .accountNumber(user.getAccountNumber())
                .build();

        return ResponseDTO.builder()
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                .accountInfo(accountInfo).build();
    }

    @Override
    public ResponseDTO accountNameEnquiry(EnquiryRequestDTO enquiryRequestDTO) {
        /* check if the account number exists */
        if (!userRepository.existsByAccountNumber(enquiryRequestDTO.accountNumber())){
            return ResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = userRepository.findByAccountNumber(enquiryRequestDTO.accountNumber());
        return ResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO
                        .builder()
                        .accountName(user.getFirstName() + " " + user.getLastName())
                        .accountNumber(user.getAccountNumber())
                        .accountBalance(user.getAccountBalance())
                        .build())
                .build();
    }
    @Transactional
    @Override
    public ResponseDTO creditAccount(CreditDebitDTO creditDebitDTO) throws MessagingException {
        /* check if accounts exists */
        if (!userRepository.existsByAccountNumber(creditDebitDTO.accountNumber())){
            return ResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToCredit = userRepository.findByAccountNumber(creditDebitDTO.accountNumber());
        BigDecimal currentAmount = userToCredit.getAccountBalance();
        userToCredit.setAccountBalance(currentAmount.add(creditDebitDTO.amount()));

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .amount(creditDebitDTO.amount())
                .accountNumber(creditDebitDTO.accountNumber())
                .transactionType("Credit")
                .status("Success")
                .build();

        transactionServiceImpl.saveTransaction(transactionDTO);

        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO
                .builder()
                .recipient(userToCredit.getEmail())
                .subject("3ank: Account Credited")
                .build();

        String message = "Your Account was credited\nAmount Credited: " + creditDebitDTO.amount();
        Context emailContext = getEmailContext(userToCredit, "3ank: Account Credited", message);

        /* Account credit email */
        emailServiceImpl.sendEmailAlert(emailDetailsDTO, emailContext,"AccountMessage");

        return ResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO
                        .builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                        .accountNumber(userToCredit.getAccountNumber())
                        .accountBalance(userToCredit.getAccountBalance())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public ResponseDTO debitAccount(CreditDebitDTO creditDebitDTO) throws MessagingException {
        User user = userRepository.findByAccountNumber(creditDebitDTO.accountNumber());

        if (user == null){
            return ResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        if (user.getAccountBalance().compareTo(creditDebitDTO.amount()) < 0){
            return ResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(AccountInfoDTO.builder()
                            .accountName(user.getFirstName() + " " + user.getLastName())
                            .accountNumber(user.getAccountNumber())
                            .accountBalance(user.getAccountBalance())
                            .build())
                    .build();
        }

        BigDecimal currentAmount = user.getAccountBalance();
        user.setAccountBalance(currentAmount.subtract(creditDebitDTO.amount()));

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .amount(creditDebitDTO.amount())
                .accountNumber(creditDebitDTO.accountNumber())
                .transactionType("Debit")
                .status("Success")
                .build();

        transactionServiceImpl.saveTransaction(transactionDTO);

        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO
                .builder()
                .recipient(user.getEmail())
                .subject("3ank: Account Debited")
                .build();

        String message = "Your Account was debited\nAmount Debited: " + creditDebitDTO.amount();
        Context emailContext = getEmailContext(user, "3ank: Account Debited", message);

        /* Account credit email */
        emailServiceImpl.sendEmailAlert(emailDetailsDTO, emailContext,"AccountMessage");

        return ResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO
                        .builder()
                        .accountNumber(user.getAccountNumber())
                        .accountBalance(user.getAccountBalance())
                        .accountName(user.getFirstName() + " " + user.getLastName())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public ResponseDTO transfer(TransferDTO transferDTO) throws MessagingException {
        User sender = userRepository.findByAccountNumber(transferDTO.senderAccountNumber());
        User receiver = userRepository.findByAccountNumber(transferDTO.receiverAccountNumber());

        /* Check if sender exists */
        if(sender == null){
            ResponseDTO.builder()
                    .accountInfo(null)
                    .responseMessage(AccountUtils.ACCOUNT_TRANSFER_INVALID_SENDER_ACCOUNT_NUMBER)
                    .responseCode(AccountUtils.ACCOUNT_TRANSFER_FAILED_CODE)
                    .build();
        }

        /* Check if receiver exists */
        if(receiver == null){
            ResponseDTO.builder()
                    .accountInfo(null)
                    .responseMessage(AccountUtils.ACCOUNT_TRANSFER_INVALID_RECEIVER_ACCOUNT_NUMBER)
                    .responseCode(AccountUtils.ACCOUNT_TRANSFER_FAILED_CODE)
                    .build();
        }

        assert sender != null;
        assert receiver != null;

        ResponseDTO senderResponse =  debitAccount(CreditDebitDTO.builder()
                .amount(transferDTO.amount())
                .accountNumber(sender.getAccountNumber())
                .build());

        if(senderResponse.accountInfo() == null || Objects.equals(senderResponse.responseCode(), "007")){
            return ResponseDTO.builder()
                    .accountInfo(null)
                    .responseMessage(senderResponse.responseMessage())
                    .responseCode(senderResponse.responseCode())
                    .build();
        }

        ResponseDTO receiverResponse = creditAccount(CreditDebitDTO.builder().
                amount(transferDTO.amount())
                .accountNumber(receiver.getAccountNumber())
                .build());

        if(receiverResponse.accountInfo() == null){
            /* Refund sender if something goes wrong */
            creditAccount(CreditDebitDTO.builder()
                    .amount(transferDTO.amount())
                    .accountNumber(sender.getAccountNumber())
                    .build());

            return ResponseDTO.builder()
                    .accountInfo(null)
                    .responseMessage(receiverResponse.responseMessage())
                    .responseCode(receiverResponse.responseCode())
                    .build();
        }

        return ResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .accountBalance(sender.getAccountBalance())
                        .accountNumber(sender.getAccountNumber())
                        .accountName(sender.getFirstName() + " " + sender.getLastName())
                        .build())
                .build();
    }

    private static Context getEmailContext(User savedUser, String subject, String message) {
        Context emailContext = new Context();
        emailContext.setVariable("name", savedUser.getFirstName() + "  " + savedUser.getLastName());
        emailContext.setVariable("subject", subject);
        emailContext.setVariable("message", message);
        emailContext.setVariable("accountNumber", savedUser.getAccountNumber());
        emailContext.setVariable("balance", savedUser.getAccountBalance());
        emailContext.setVariable("year", Year.now());
        return emailContext;
    }

}
