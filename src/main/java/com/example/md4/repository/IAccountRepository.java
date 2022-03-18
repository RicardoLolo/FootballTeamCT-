package com.example.md4.repository;

import com.example.md4.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByGmail(String username);

    Boolean existsByGmail(String username);

    Optional<Account> findAccountByGmailAndPassword(String username,String password);

    @Query(value = "select * from account acc \n" +
            "where acc.coach_id = ?1 ;", nativeQuery = true)
    Account findAccountByCoach(Long id);

    @Query(value = "select * from account acc \n" +
            "where acc.player_id = ?1 ;", nativeQuery = true)
    Account findAccountByPlayer(Long id);

    @Query(value = "delete from account acc \n" +
            "where acc.coach_id = ?1 ;", nativeQuery = true)
    void deleteAccountByCoach(Long id);

    @Query(value = "delete from account acc \n" +
            "where acc.player_id = ?1 ;", nativeQuery = true)
    void deleteAccountByPlayer(Long id);
}
