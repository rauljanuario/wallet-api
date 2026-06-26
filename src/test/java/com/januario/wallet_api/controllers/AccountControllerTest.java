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

import java.math.BigDecimal;
import java.util.List;

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

    @Test
    @DisplayName("Return a accounts list")
    void getAccount_WithValidData_ReturnAAccountList() {

        // ARRANGE
        Account dados = new Account();
        dados.setHolderName("Raul");
        dados.setId(1L);
        dados.setRole(UserRole.ROLE_ADMIN);

        List<Account> myAccount = List.of(dados);

        Mockito.when(accountRepository.findAll()).thenReturn(myAccount);

        // ACT
        var result = accountController.getAccount();

        // ASSERT
        GetAccountDTO firstData = result.getFirst();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(myAccount.getFirst().getHolderName(), firstData.holderName());
        Assertions.assertEquals(myAccount.getFirst().getId(), firstData.id());
        Assertions.assertEquals(myAccount.getFirst().getRole(), firstData.role());
        Mockito.verify(accountRepository, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("Return specific account infos")
    void getMyData_WithValidData_ReturnSpecificAccountInfos() {

        // ARRANGE
        Account newAccount = new Account();
        newAccount.setId(1L);
        newAccount.setHolderName("Raul");
        newAccount.setRole(UserRole.ROLE_USER);

        // ACT
        var result = accountController.getMyData(newAccount);

        // ASSERT
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(newAccount.getHolderName(), result.getBody().holderName());
        Assertions.assertEquals(newAccount.getId(), result.getBody().id());
        Assertions.assertEquals(newAccount.getRole(), result.getBody().role());

    }

    @Test
    @DisplayName("Return all account infos")
    void getMyInfo_WithValidData_ReturnAllAccountInfos() {

        // ARRANGE
        Account newAccount = new Account();
        newAccount.setHolderName("Raul");
        newAccount.setId(1L);
        newAccount.setCpf("11122233344");
        newAccount.setActive(true);
        newAccount.setRole(UserRole.ROLE_USER);
        newAccount.setBalance(BigDecimal.valueOf(1000));

        // ACT
        var result = accountController.getMyInfo(newAccount);

        //ASSET
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(newAccount.getHolderName(), result.getBody().holderName());
        Assertions.assertEquals(newAccount.getId(), result.getBody().id());
        Assertions.assertEquals(newAccount.getCpf(), result.getBody().cpf());
        Assertions.assertEquals(newAccount.isActive(), result.getBody().active());
        Assertions.assertEquals(newAccount.getRole(), result.getBody().role());
        Assertions.assertEquals(newAccount.getBalance(), result.getBody().balance());


    }

    @Test
    void deleteAccount() {
    }
}