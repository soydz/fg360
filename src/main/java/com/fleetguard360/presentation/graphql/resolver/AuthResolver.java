package com.fleetguard360.presentation.graphql.resolver;

import com.fleetguard360.presentation.graphql.dto.AuthResDTO;
import com.fleetguard360.service.exception.InvalidCredentialsException;
import com.fleetguard360.service.interfaces.AuthService;
import feign.FeignException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.lang.module.FindException;

@Controller
public class AuthResolver {

    private final AuthService authService;

    public AuthResolver(AuthService authService) {
        this.authService = authService;
    }

    @MutationMapping
    public AuthResDTO login(@Argument String email, @Argument String password) {
        try {
            return authService.login(email, password);

        } catch (FeignException e) {
            if (e.status() == 401 || e.status() == 404) {
                throw new InvalidCredentialsException("Login failed: Bad credentials");
            }
            throw new FindException("Login service error");
        }
    }
}
