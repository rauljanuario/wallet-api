package com.januario.wallet_api.dtos.accountDTO;

import com.januario.wallet_api.models.UserRole;

import java.math.BigDecimal;

public record GetInfoDTO(

        Long id,
        String holderName,
        String cpf,
        BigDecimal balance,
        UserRole role

) {
}
