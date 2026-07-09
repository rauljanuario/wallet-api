package com.januario.wallet_api.dtos.accountDTO;

import com.januario.wallet_api.models.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PostAccountDTO(

        @NotBlank
        @Size(min = 3, max = 40)
        String holderName,

        @NotBlank
        @CPF
        String cpf,

        @NotBlank
        @Size(min = 8, max = 20)
        String password,

        @NotNull
        UserRole role

) { }
