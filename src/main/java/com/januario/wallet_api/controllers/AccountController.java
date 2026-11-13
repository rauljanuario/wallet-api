package com.januario.wallet_api.controllers;


import com.januario.wallet_api.dtos.accountDTO.GetAccountDTO;
import com.januario.wallet_api.dtos.accountDTO.GetInfoDTO;
import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.repositories.AccountRepository;
import com.januario.wallet_api.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountController (AccountRepository accountRepository, AccountService accountService){
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }


    @PostMapping("/create")
    public ResponseEntity<GetAccountDTO> createAccount (@RequestBody @Validated PostAccountDTO data) {

        GetAccountDTO result = accountService.createAccount(data).getBody();

        return ResponseEntity.ok(result);

    }

    @GetMapping
    public List<GetAccountDTO> getAllAccounts (){
        return accountRepository.findAll().stream()
                .map(account -> new GetAccountDTO(account.getId(), account.getHolderName(), account.getRole()))
                .toList();
    }

    @GetMapping("/me")
    public ResponseEntity<GetAccountDTO> getSimpleInfoAccount (@AuthenticationPrincipal Account account){

        return ResponseEntity.ok(new GetAccountDTO(account.getId(), account.getHolderName(), account.getRole()));

    }

    @GetMapping("/me/info")
    public ResponseEntity<GetInfoDTO> getCompleteInfoAccount (@AuthenticationPrincipal Account account){

        return ResponseEntity.ok(new GetInfoDTO(

                account.getId(),
                account.getHolderName(),
                account.getCpf(),
                account.getBalance(),
                account.getRole(),
                account.isActive()

        ));

    }


    @PutMapping("/{id}")
    public void deleteAccount (@PathVariable Long id){

        accountService.delete(id);

    }




}
