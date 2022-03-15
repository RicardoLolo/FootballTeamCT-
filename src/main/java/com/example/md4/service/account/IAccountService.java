package com.example.md4.service.account;

import com.example.md4.model.Account;
import com.example.md4.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IAccountService extends IGeneralService<Account>, UserDetailsService {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByGmail(String username);

    Boolean existsByGmail(String username);
}
