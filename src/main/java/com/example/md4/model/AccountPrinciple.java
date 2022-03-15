package com.example.md4.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class AccountPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String emailAddress;

    private String password;

    private Collection<? extends GrantedAuthority> roles;

    public AccountPrinciple(Long id,
                         String emailAddress, String password,
                         Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles = roles;
    }

    public static AccountPrinciple build(Account user) {
        GrantedAuthority authorities =  new SimpleGrantedAuthority(user.getRoles().getRole());

        return new AccountPrinciple(
                user.getId(),
                user.getGmail(),
                user.getPassword(),
                Collections.singleton(authorities)
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountPrinciple acc = (AccountPrinciple) o;
        return Objects.equals(id, acc.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
