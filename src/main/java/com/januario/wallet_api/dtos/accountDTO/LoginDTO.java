package com.januario.wallet_api.dtos.accountDTO;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String cpf, @NotBlank String password) {
}
