package com.netmind.oauth2authorizationserver.controller;

import com.netmind.oauth2authorizationserver.model.ERole;
import com.netmind.oauth2authorizationserver.model.User;
import com.netmind.oauth2authorizationserver.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserServiceController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> products = userRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/setup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> setUpTestUser() {
        User newUser = new User(null, "r@r.com", "pa55wrd", ERole.USER);
        createUser(newUser);
        return getAllUsers();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody @Valid User newUser) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String enc_password = passwordEncoder.encode(newUser.getPassword());

        newUser.setId(null);
        newUser.setPassword(enc_password);
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
