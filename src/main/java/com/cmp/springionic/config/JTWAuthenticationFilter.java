package com.cmp.springionic.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.domain.enums.Role;
import com.cmp.springionic.repositories.ClientRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JTWAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	JWTTokenService tokenService;
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		var token = this.recoverToken(request);
		
		var login = tokenService.validateToken(token);

		if (login) {
			String username = tokenService.getUsername(token);
			Client client = clientRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found"));

			List<SimpleGrantedAuthority> authorities = client.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(Role.toEnum(role.getCod()).getDescription()))
					.collect(Collectors.toList());
			
			var authentication = new UsernamePasswordAuthenticationToken(client, null, authorities);
			
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);				
			}
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");			
		}
		return null;
	}
}
