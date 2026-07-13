package com.januario.wallet_api.controllers;

import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.models.UserRole;
import com.januario.wallet_api.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AccountControllerIT {

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    void createNewAccount_WithValidData_returnAGetDTO(){

        PostAccountDTO dtoFict =
                new PostAccountDTO("Raul", "11122233345", "raul123", UserRole.ROLE_ADMIN);


        this.accountController.newAccount(dtoFict);

        List<Account> accounts = this.accountRepository.findAll();

        Assertions.assertEquals(1, accounts.size());
        Assertions.assertEquals("Raul", accounts.getFirst().getHolderName());

    }


}
