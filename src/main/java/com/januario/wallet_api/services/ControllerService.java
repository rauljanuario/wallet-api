package com.januario.wallet_api.services;

import com.januario.wallet_api.dtos.accountDTO.GetAccountDTO;
import com.januario.wallet_api.dtos.accountDTO.PostAccountDTO;
import com.januario.wallet_api.exceptions.ErrorCreateAccount;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.repositories.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ControllerService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public ControllerService (AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository=accountRepository;
        this.passwordEncoder=passwordEncoder;

    }

    public ResponseEntity<GetAccountDTO> createAccount (@RequestBody PostAccountDTO data) throws ErrorCreateAccount{

        if (accountRepository.existsByCpf(data.cpf())){
            throw new ErrorCreateAccount("This CPF already exists");
        }

        var newAccount = new Account();
        newAccount.setHolderName(data.holderName());
        newAccount.setCpf(data.cpf());
        newAccount.setRole(data.role());

        String hashPassword = passwordEncoder.encode(data.password());
        newAccount.setPassword(hashPassword);

        accountRepository.save(newAccount);

        return ResponseEntity.ok(
                new GetAccountDTO(newAccount.getId(), newAccount.getHolderName(), data.role()
                )
        ) ;
    }

    public void delete (Long id){

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setActive(false);

        accountRepository.save(account);

    }

}
