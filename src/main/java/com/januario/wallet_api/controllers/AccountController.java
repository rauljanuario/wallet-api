package com.januario.wallet_api.controllers;


import com.januario.wallet_api.dtos.accountDTO.GetAccountDTO;
import com.januario.wallet_api.dtos.accountDTO.GetInfoDTO;
import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.exceptions.ErrorCreateAccount;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.repositories.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountController (AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/create")
    public ResponseEntity<GetAccountDTO> createAccount (@RequestBody PostAccountDTO data) throws ErrorCreateAccount {

        var newAccount = new Account();
        newAccount.setHolderName(data.holderName());
        newAccount.setCpf(data.cpf());
        newAccount.setRole(data.role());

        String hashPassword = passwordEncoder.encode(data.password());
        newAccount.setPassword(hashPassword);

        boolean existsCpf = accountRepository.existsByCpf(data.cpf());

        if (existsCpf){
            throw new ErrorCreateAccount("This CPF already exists");
        }

        accountRepository.save(newAccount);

        return ResponseEntity.ok(
                new GetAccountDTO(newAccount.getId(), newAccount.getHolderName(), data.role()
                )
        ) ;

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
