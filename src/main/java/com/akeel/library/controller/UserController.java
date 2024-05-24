package com.akeel.library.controller;


import com.akeel.library.model.JwtRequest;
import com.akeel.library.model.JwtResponse;
import com.akeel.library.model.Role;
import com.akeel.library.model.User;
import com.akeel.library.repository.UserRepository;
import com.akeel.library.security.JwtTokenUtil;
import com.akeel.library.service.JwtUserDetailsService;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "authenticate, register new user and list all users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.akeel.library.repository.intefaces.RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/all")
    @Cacheable(value = "users", key = "", sync = true)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole != null) {
                roles.add(existingRole);
            } else {
                roles.add(role);
            }
        }
        user.setRoles(roles);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
