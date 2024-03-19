package com.phung.clothshop.domain.entity.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> roles;

    private final String eRole;

    public AccountPrinciple(Long id, String username, String password,
            Collection<? extends GrantedAuthority> roles, ERole eRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.eRole = eRole.toString();
    }

    public static AccountPrinciple build(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getERole().toString());
        authorities.add(authority);

        return new AccountPrinciple(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                authorities,
                account.getERole());

    }

    public Long getId() {
        return id;
    }

    public String getERole() {
        return eRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

}
