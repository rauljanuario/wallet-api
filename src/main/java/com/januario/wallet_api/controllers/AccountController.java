package com.januario.wallet_api.controllers;


import com.januario.wallet_api.dtos.accountDTO.GetAccountDTO;
import com.januario.wallet_api.dtos.accountDTO.GetInfoDTO;
import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.exceptions.ErrorCreateAccount;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.repositories.AccountRepository;
import com.januario.wallet_api.services.ControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountRepository accountRepository;
    private final ControllerService controllerService;

    public AccountController (AccountRepository accountRepository, ControllerService controllerService){
        this.accountRepository = accountRepository;
        this.controllerService = controllerService;
    }


    @PostMapping("/create")
    public ResponseEntity<GetAccountDTO> account (@RequestBody PostAccountDTO data) {

        GetAccountDTO result = controllerService.createAccount(data).getBody();

        return ResponseEntity.ok(result);

    }

    @GetMapping
    public List<GetAccountDTO> getAccount (){
        return accountRepository.findAll().stream()
                .map(account -> new GetAccountDTO(account.getId(), account.getHolderName(), account.getRole()))
                .toList();
    }

    @GetMapping("/me")
    public ResponseEntity<GetAccountDTO> getMyData(@AuthenticationPrincipal Account account){

        return ResponseEntity.ok(new GetAccountDTO(account.getId(), account.getHolderName(), account.getRole()));

    }

    @GetMapping("/me/info")
    public ResponseEntity<GetInfoDTO> getMyInfo(@AuthenticationPrincipal Account account){

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

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setActive(false);

        accountRepository.save(account);

    }




}
