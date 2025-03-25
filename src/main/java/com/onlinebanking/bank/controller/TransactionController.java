package com.onlinebanking.bank.controller;

import com.onlinebanking.bank.dto.GenerateStatementDTO;
import com.onlinebanking.bank.entity.Transaction;
import com.onlinebanking.bank.service.impl.BankStatement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankStatement")
@RequiredArgsConstructor
public class TransactionController {
  private final BankStatement bankStatement;

  @GetMapping
  public List<Transaction> transactionList(@RequestBody GenerateStatementDTO generateStatementDTO) {
    GenerateStatementDTO statementDTO = GenerateStatementDTO.builder()
        .accountNumber(generateStatementDTO.accountNumber())
        .startDate(generateStatementDTO.startDate())
        .endDate(generateStatementDTO.endDate())
        .build();
    return bankStatement.generateStatement(statementDTO);
  }
}
