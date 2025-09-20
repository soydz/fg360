package com.fleetguard360.service.implementation;

import com.fleetguard360.persistence.entity.User;
import com.fleetguard360.presentation.dto.AuthResDTO;
import com.fleetguard360.service.exception.InvalidCredentialsException;
import com.fleetguard360.service.interfaces.AuthService;
import com.fleetguard360.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthResDTO login(String email, String password) {
        User user = userService.getByEmail(email);

        if (!user.getPassword().equals(password)) {
            log.error("Credenciales no validas");
            throw new InvalidCredentialsException("Credenciales no validas");
        }
        log.info("Credenciales aceptadas");

        return new AuthResDTO(
                user.getId(),
                user.getEmail(),
                "Loggeado",
                "JWT en proceso",
                true);
    }
}
