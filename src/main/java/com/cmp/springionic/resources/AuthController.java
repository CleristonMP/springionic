package com.cmp.springionic.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmp.springionic.config.JWTTokenService;
import com.cmp.springionic.domain.Client;
import com.cmp.springionic.dto.CredentialsDTO;
import com.cmp.springionic.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final ClientRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JWTTokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<CredentialsDTO> login(@RequestBody CredentialsDTO body) {
		Client client = this.repository.findByEmail(body.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		if (passwordEncoder.matches(body.getPassword(), client.getPassword())) {
			String token = this.tokenService.generateToken(client);
			return ResponseEntity.ok(new CredentialsDTO(client.getEmail(), token));
		}
		return ResponseEntity.badRequest().build();
	}
}
