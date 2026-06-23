package com.januario.wallet_api.controllers;

import com.januario.wallet_api.dtos.accountDTO.GetAccountDTO;
import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.models.UserRole;
import com.januario.wallet_api.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("Return a new account")
    void createAccount_WithValidData_ReturnAGetDTO() {

        // ARRANGE
        PostAccountDTO dtoFict =
                new PostAccountDTO("Raul", "11122233345", "raul123", UserRole.ROLE_ADMIN);

        Account newAccount = new Account();

        Mockito.when(passwordEncoder.encode(dtoFict.password())).thenReturn("senhaMocada123");
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(newAccount);

        // ACT
        var result = accountController.createAccount(dtoFict);

        // ASSERT
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(dtoFict.holderName(), result.getBody().holderName());

    }
}