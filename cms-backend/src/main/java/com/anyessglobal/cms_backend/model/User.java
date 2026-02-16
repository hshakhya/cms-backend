package com.anyessglobal.cms_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data // Lombok: Adds getters, setters, toString, equals, hashCode
@Builder // Lombok: Implements the builder pattern
@NoArgsConstructor // Lombok: Creates a no-args constructor
@AllArgsConstructor // Lombok: Creates an all-args constructor
@Entity // JPA: Marks this class as a database entity
@Table(name = "users") // JPA: Specifies the table name
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email; // This will be our username

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Stores the role as a string ("ROLE_ADMIN")
    @Column(nullable = false)
    private Role role;

    // UserDetails Methods(Required by Spring Security)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // We return a list containing just our single role
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // We have used email as the username
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is always enabled
    }
}