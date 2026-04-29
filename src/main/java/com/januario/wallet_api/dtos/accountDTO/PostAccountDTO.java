package com.januario.wallet_api.dtos.accountDTO;

import com.januario.wallet_api.models.UserRole;
import jakarta.validation.constraints.NotBlank;

public record PostAccountDTO(

        @NotBlank String holderName,
        @NotBlank String cpf,
        @NotBlank String password,
        @NotBlank UserRole role

) { }
