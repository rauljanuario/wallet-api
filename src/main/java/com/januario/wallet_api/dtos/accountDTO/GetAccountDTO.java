package com.januario.wallet_api.dtos.accountDTO;

import com.januario.wallet_api.models.UserRole;

public record GetAccountDTO(

        Long id,
        String holderName,
        UserRole role

) { }
