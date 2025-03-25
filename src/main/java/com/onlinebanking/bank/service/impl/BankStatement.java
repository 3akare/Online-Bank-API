package com.onlinebanking.bank.service.impl;

import com.onlinebanking.bank.dto.GenerateStatementDTO;
import com.onlinebanking.bank.entity.Transaction;
import com.onlinebanking.bank.repository.TransactionRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BankStatement {
  private final TransactionRepository transactionRepository;

  public List<Transaction> generateStatement(GenerateStatementDTO statementDTO){
    LocalDate start = LocalDate.parse(statementDTO.startDate(), DateTimeFormatter.ISO_DATE);
    LocalDate end = LocalDate.parse(statementDTO.endDate(), DateTimeFormatter.ISO_DATE);

    return transactionRepository.findAll()
        .stream()
        .filter(transaction ->
            transaction.getAccountNumber().equals(statementDTO.accountNumber()))
        .filter(transaction ->
            !transaction.getCreatedAt().isBefore(start))
        .filter(transaction ->
            !transaction.getCreatedAt().isAfter(end))
        .toList();
  }
}
