package com.januario.wallet_api.repositories;

import com.januario.wallet_api.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderAccount_IdOrReceiverAccount_Id(Long senderId, Long receiverId);

}
