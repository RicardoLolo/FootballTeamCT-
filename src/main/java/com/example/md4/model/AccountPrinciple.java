package com.example.md4.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String gmailAddress;

    private String password;

    private Collection<? extends GrantedAuthority> roles;

    public AccountPrinciple(Long id,
                            String gmailAddress, String password,
                            Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.gmailAddress = gmailAddress;
        this.password = password;
        this.roles = roles;
    }

    public static AccountPrinciple build(Account user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());

        return new AccountPrinciple(
                user.getId(),
                user.getGmail(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return gmailAddress;
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