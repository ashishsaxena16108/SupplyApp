package com.dms.supplymanager.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Document(collection = "users")
@Data
@AllArgsConstructor
public class User implements UserDetails {
    private String Id;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String warehouseId;
    private Integer phoneNumber;
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(isAdmin() ? "ROLE_ADMIN" : "ROLE_USER"));
    }
    public boolean isAdmin(){
        return role==Role.ADMIN;
    }
    @Override
    public String getUsername(){
        return email;
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
    public enum Role{
        INSPECTOR,ADMIN
    }
}
