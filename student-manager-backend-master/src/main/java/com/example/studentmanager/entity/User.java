package com.example.studentmanager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;


@Entity
@Table(name="USER")
public class User implements UserDetails {
    // 学生的用户名为学号，教职的用户名为工号
    @Id
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    @Transient
    private UserType userType;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                ", userType=" + userType +
                '}';
    }

    enum UserType {
        STUDENT,
        STAFF
    }

    public User() {

    }

    public User(String username, String password, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        // 根据用户名长度判断用户
        this.userType = username.length() > 6 ? UserType.STUDENT : UserType.STAFF;
    }

    public User(String username, String password, String email, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, email, enabled);
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }
}
