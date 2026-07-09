package com.januario.wallet_api.dtos.accountDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record LoginDTO(

        @NotBlank
        @CPF
        String cpf,

        @NotBlank
        @Size(min = 8, max = 20)
        String password

) {
}
