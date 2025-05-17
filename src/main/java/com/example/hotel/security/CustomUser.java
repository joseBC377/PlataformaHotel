package com.example.hotel.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails {


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getPassword() {

        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public String getUsername() {

        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

}
