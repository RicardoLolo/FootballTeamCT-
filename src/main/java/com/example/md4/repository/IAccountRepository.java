package com.example.md4.repository;

import com.example.md4.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByGmail(String username);
    Boolean existsByGmail(String username);
}
