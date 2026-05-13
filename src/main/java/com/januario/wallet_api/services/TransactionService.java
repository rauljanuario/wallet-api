package com.januario.wallet_api.services;

import com.januario.wallet_api.dtos.transactionDTO.DepositDTO;
import com.januario.wallet_api.dtos.transactionDTO.TransactionResponseDTO;
import com.januario.wallet_api.models.Transaction;
import com.januario.wallet_api.dtos.transactionDTO.TransferDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.models.TransactionType;
import com.januario.wallet_api.repositories.AccountRepository;
import com.januario.wallet_api.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }


    @Transactional
    public TransactionResponseDTO executeTransfers (TransferDTO data){

        Account sender = accountRepository.findById(data.senderAccountId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findById(data.receiverAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (sender.getBalance().compareTo(data.value()) < 0){
            throw new RuntimeException("Insufficient balance to complete the transfer");
        }

        sender.setBalance(sender.getBalance().subtract(data.value()));
        receiver.setBalance(receiver.getBalance().add(data.value()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        var transaction = new Transaction();
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(data.value());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());

        Transaction saved = transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                saved.getId(),
                saved.getSenderAccount().getHolderName(),
                saved.getReceiverAccount().getHolderName(),
                saved.getAmount(),
                saved.getType().toString(),
                saved.getTimestamp()
        );
    }

    @Transactional
    public Transaction executeDeposit (DepositDTO data){

        Account receiver = accountRepository.findById(data.receiverAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        receiver.setBalance(receiver.getBalance().add(data.value()));

        accountRepository.save(receiver);

        var transaction = new Transaction();
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(data.value());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);


    }




}
