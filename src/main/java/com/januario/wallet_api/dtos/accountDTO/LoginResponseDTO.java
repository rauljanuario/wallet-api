package com.januario.wallet_api.dtos.accountDTO;

import com.januario.wallet_api.models.UserRole;

public record LoginResponseDTO(String token, UserRole role) {
}
