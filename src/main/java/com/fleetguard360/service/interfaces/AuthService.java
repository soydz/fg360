package com.fleetguard360.service.interfaces;

import com.fleetguard360.presentation.graphql.dto.AuthResDTO;

public interface AuthService {

    AuthResDTO login(String email, String password);
}
