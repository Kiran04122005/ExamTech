package com.examtech.service;

import com.examtech.model.User;
import com.examtech.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= REGISTER USER =================

    public User registerUser(User user) {

        // Every user registering through the public
        // registration page is automatically a Student.
        user.setRole("STUDENT");

        // Remove accidental spaces from the email.
        if (user.getEmail() != null) {
            user.setEmail(user.getEmail().trim().toLowerCase());
        }

        // Encrypt the password before storing it.
        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    // ================= FIND USER BY EMAIL =================

    public Optional<User> findUserByEmail(String email) {

        if (email == null) {
            return Optional.empty();
        }

        return userRepository.findByEmail(
                email.trim().toLowerCase()
        );
    }

    // ================= CHECK USER EXISTS =================

    public boolean userExists(String email) {

        if (email == null) {
            return false;
        }

        return userRepository.findByEmail(
                email.trim().toLowerCase()
        ).isPresent();
    }

    // ================= UPDATE USER =================

    public User updateUser(User user) {

        if (user.getEmail() != null) {
            user.setEmail(
                    user.getEmail().trim().toLowerCase()
            );
        }

        return userRepository.save(user);
    }
}