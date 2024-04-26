package com.deom.BookManagementSystem.service.authService;

import com.deom.BookManagementSystem.exchanges.AuthenticationRequest;
import com.deom.BookManagementSystem.exchanges.AuthenticationResponse;
import com.deom.BookManagementSystem.exchanges.RegisterRequest;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest registerRequest);

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest);
}
