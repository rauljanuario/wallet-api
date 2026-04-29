package com.januario.wallet_api.dtos.transactionDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(

    Long id,
    String nameSender,
    String nameReceiver,
    BigDecimal value,
    String type,
    LocalDateTime dataHora

){}
