package com.januario.wallet_api.controllers;

import com.januario.wallet_api.dtos.accountDTO.LoginDTO;
import com.januario.wallet_api.dtos.accountDTO.LoginResponseDTO;
import com.januario.wallet_api.models.Account;
import com.januario.wallet_api.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO data) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(data.cpf(), data.password());

        var authentication = manager.authenticate(authenticationToken);

        var account = (Account) authentication.getPrincipal();

        var tokenJWT = tokenService.generateToken(account);

        return  ResponseEntity.ok(new LoginResponseDTO(tokenJWT, account.getRole()));
    }




}
