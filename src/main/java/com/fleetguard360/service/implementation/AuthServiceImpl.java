package com.fleetguard360.service.implementation;

import com.fleetguard360.infrastructure.feight.client.AuthClient;
import com.fleetguard360.infrastructure.feight.dto.AuthReqFeightDTO;
import com.fleetguard360.presentation.graphql.dto.AuthResDTO;
import com.fleetguard360.service.interfaces.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    public AuthServiceImpl(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public AuthResDTO login(String email, String password) {
        return AuthResDTO.toAuthResDTO(
                authClient.login(new AuthReqFeightDTO(email, password))
        );
    }
}
