package com.cmp.springionic.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserSS user = new UserSS(principal.getId(), principal.getEmail(), principal.getPassword(),
					principal.getRoles());
			return user;
		} catch (Exception e) {
			return null;
		}
	}
}
