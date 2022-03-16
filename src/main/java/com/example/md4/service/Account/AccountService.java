package com.example.md4.service.Account;

import com.example.md4.model.Account;
import com.example.md4.model.AccountPrinciple;
import com.example.md4.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Optional<Account> findByGmail(String username) {
        return accountRepository.findByGmail(username);
    }

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void remove(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> userOptional = accountRepository.findByGmail(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return AccountPrinciple.build(userOptional.get());
    }

    @Override
    public Boolean existsByGmail(String username) {
        return accountRepository.existsByGmail(username);
    }

    @Override
    public Optional<Account> findAccountByGmailAndPassword(String username, String password) {
        return accountRepository.findAccountByGmailAndPassword(username, password);
    }
}
