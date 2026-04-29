package com.januario.wallet_api.repositories;

import com.januario.wallet_api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountRepository extends JpaRepository<Account, Long> {

    UserDetails findByCpf(String cpf);
}
