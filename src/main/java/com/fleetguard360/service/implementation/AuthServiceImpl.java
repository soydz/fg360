package com.fleetguard360.service.implementation;

import com.fleetguard360.presentation.dto.AuthResDTO;
import com.fleetguard360.service.exception.InvalidCredentialsException;
import com.fleetguard360.service.interfaces.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResDTO login(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserDetails user = (UserDetails) authentication.getPrincipal();

            log.info("Credenciales aceptadas");
            return new AuthResDTO(
                    0L,
                    user.getUsername(),
                    "Loggeado",
                    "JWT en proceso",
                    user.isEnabled());
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Bad credentials");
        }
    }
}
