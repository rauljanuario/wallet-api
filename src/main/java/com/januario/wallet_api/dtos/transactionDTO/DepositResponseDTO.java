package com.januario.wallet_api.dtos.transactionDTO;

import java.math.BigDecimal;

public record DepositResponseDTO(

        String receiverAccount,
        BigDecimal amount

) {
}
