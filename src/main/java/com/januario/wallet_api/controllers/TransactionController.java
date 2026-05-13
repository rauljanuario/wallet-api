package com.januario.wallet_api.controllers;

import com.januario.wallet_api.dtos.transactionDTO.DepositDTO;
import com.januario.wallet_api.dtos.transactionDTO.DepositResponseDTO;
import com.januario.wallet_api.dtos.transactionDTO.TransactionResponseDTO;
import com.januario.wallet_api.dtos.transactionDTO.TransferDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.models.Transaction;
import com.januario.wallet_api.repositories.TransactionRepository;
import com.januario.wallet_api.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository){
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;

    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> transfer(@RequestBody @Valid TransferDTO data){

        TransactionResponseDTO result = transactionService.executeTransfers(data);

        return ResponseEntity.ok(result);

    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponseDTO> deposit(@RequestBody @Valid DepositDTO data){

        Transaction result = transactionService.executeDeposit(data);

        var response = new DepositResponseDTO(

                result.getReceiverAccount().getHolderName(),
                result.getAmount()

        );

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public List<TransactionResponseDTO> transactions(@AuthenticationPrincipal Account account){

        return transactionRepository
                .findBySenderAccount_IdOrReceiverAccount_Id(account.getId(), account.getId()).stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getSenderAccount() != null ? transaction.getSenderAccount().getHolderName() : "EXTERNAL DEPOSIT",
                        transaction.getReceiverAccount().getHolderName(),
                        transaction.getAmount(),
                        transaction.getType().toString(),
                        transaction.getTimestamp()
                ))
                .toList();
    }


}
