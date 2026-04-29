package com.januario.wallet_api.dtos.transactionDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferDTO(@NotNull Long senderAccountId, @NotNull Long receiverAccountId, @NotNull @Positive BigDecimal value) {
}
