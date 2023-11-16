package com.tfm.afac.services;


import com.tfm.afac.data.daos.UserRepository;
import com.tfm.afac.data.model.Role;
import com.tfm.afac.data.model.User;
import com.tfm.afac.services.exceptions.ConflictException;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Optional< String > login(String email) {
        System.out.println("login email: " +email);
        //Optional<User> byEmail = userRepository.findByEmail(email);

        return this.userRepository.findByEmail(email)
                .map(user -> jwtService.createToken(user.getEmail(), user.getFirstName(), user.getRole().name()));
    }

    public void createUser(User user, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(user.getRole())) {
            throw new ForbiddenException("Insufficient role to create this user: " + user);
        }
        this.assertNoExistByMobile(user.getEmail());
        user.setRegistrationDate(LocalDateTime.now());
        this.userRepository.save(user);
    }

    public Stream< User > readAll(Role roleClaim) {
        return this.userRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

    private List< Role > authorizedRoles(Role roleClaim) {
        if (Role.ADMIN.equals(roleClaim)) {
            return List.of(Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.MANAGER.equals(roleClaim)) {
            return List.of(Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.OPERATOR.equals(roleClaim)) {
            return List.of(Role.CUSTOMER);
        } else {
            return List.of();
        }
    }

    private void assertNoExistByMobile(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("The emal already exists: " + email);
        }
    }

    public User readByMobileAssured(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("The email don't exist: " + email));
    }
}
