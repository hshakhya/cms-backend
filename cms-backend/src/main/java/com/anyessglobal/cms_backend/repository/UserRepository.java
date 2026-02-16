package com.anyessglobal.cms_backend.repository;

import com.anyessglobal.cms_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // This is the critical method Spring Security will use
    // to find a user by their email (which we use as the username).
    Optional<User> findByEmail(String email);

}