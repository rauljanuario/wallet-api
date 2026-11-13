package com.januario.wallet_api.services;

import com.januario.wallet_api.dtos.transactionDTO.PostDepositDTO;
import com.januario.wallet_api.dtos.transactionDTO.TransactionResponseDTO;
import com.januario.wallet_api.models.Transaction;
import com.januario.wallet_api.dtos.transactionDTO.PostTransferDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.models.TransactionType;
import com.januario.wallet_api.repositories.AccountRepository;
import com.januario.wallet_api.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public TransactionResponseDTO executeTransfer(PostTransferDTO data){

        Account sender = findAccountById(data.senderAccountId());
        Account receiver = findAccountById(data.receiverAccountId());

        hasSufficientBalance(sender, data.value());

        updateSenderAndReceiverBalances(sender, receiver, data.value());

        Transaction transaction = createTransfer(sender, receiver, data.value());

        Transaction saved = transactionRepository.save(transaction);

        return convertToTransactionResponseDTO(saved);

    }

    @Transactional
    public Transaction executeDeposit (PostDepositDTO data){

        Account receiver = accountRepository.findById(data.receiverAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver newAccount not found"));

        receiver.setBalance(receiver.getBalance().add(data.value()));

        accountRepository.save(receiver);

        var transaction = new Transaction();
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(data.value());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);


    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    private void hasSufficientBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance to complete the transfer");
        }
    }

    private void updateSenderAndReceiverBalances(Account sender, Account receiver, BigDecimal amount) {
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
    }

    private Transaction createTransfer(Account sender, Account receiver, BigDecimal amount) {
        var transaction = new Transaction();
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    private TransactionResponseDTO convertToTransactionResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getSenderAccount().getHolderName(),
                transaction.getReceiverAccount().getHolderName(),
                transaction.getAmount(),
                transaction.getType().toString(),
                transaction.getTimestamp()
        );
    }
}
