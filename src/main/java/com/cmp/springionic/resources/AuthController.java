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
import com.cmp.springionic.security.UserSS;
import com.cmp.springionic.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
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
		Client client = this.repository.findByEmail(body.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));
		if (passwordEncoder.matches(body.getPassword(), client.getPassword())) {
			String token = this.tokenService.generateToken(client);
			return ResponseEntity.ok(new CredentialsDTO(client.getEmail(), token));
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		Client client = new Client(user.getId(), null, user.getUsername(), null, null, user.getPassword());
		String token = tokenService.generateToken(client);
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}
