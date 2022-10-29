package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);
    //TODO index on username column
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String number);
    Optional<User> findByEmail(String email);


}
