package com.autobots.app.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.types.dtos.CredencialDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Authentication related endpoints")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Operation(summary = "Login", description = "Authenticate user and return token")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody CredencialDTO credencialDTO) {
        return ResponseEntity.noContent().build();
        // No implementation, just for docs
    }

    @Operation(summary = "Logout", description = "Invalidate user session or token")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
        // No implementation, just for docs
    }
}
