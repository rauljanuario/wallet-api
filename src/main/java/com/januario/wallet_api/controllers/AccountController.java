package com.januario.wallet_api.controllers;


import com.januario.wallet_api.dtos.accountDTO.GetAccountDTO;
import com.januario.wallet_api.dtos.accountDTO.GetInfoDTO;
import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/create")
    public ResponseEntity<GetAccountDTO> createAccount (@RequestBody PostAccountDTO data){

        var newAccount = new Account();
        newAccount.setHolderName(data.holderName());
        newAccount.setCpf(data.cpf());
        newAccount.setRole(data.role());

        String hashPassword = passwordEncoder.encode(data.password());
        newAccount.setPassword(hashPassword);

        accountRepository.save(newAccount);

        return ResponseEntity.ok(new GetAccountDTO(newAccount.getId(), newAccount.getHolderName(), data.role()));

    }

    @GetMapping
    public List<GetAccountDTO> getAccount (){
        return accountRepository.findAll().stream()
                .map(account -> new GetAccountDTO(account.getId(), account.getHolderName(), account.getRole()))
                .toList();
    }

    @GetMapping("/me")
    public ResponseEntity getMyData(@AuthenticationPrincipal Account account){

        return ResponseEntity.ok(new GetAccountDTO(account.getId(), account.getHolderName(), account.getRole()));

    }

    @GetMapping("/me/info")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal Account account){

        return ResponseEntity.ok(new GetInfoDTO(

                account.getId(),
                account.getHolderName(),
                account.getCpf(),
                account.getBalance(),
                account.getRole()

        ));

    }


    @DeleteMapping("/{id}")
    public void deleteAccount (@PathVariable Long id){
        accountRepository.deleteById(id);
    }




}
