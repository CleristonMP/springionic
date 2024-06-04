package com.cmp.springionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.repositories.ClientRepository;
import com.cmp.springionic.security.UserSS;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Client cli = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		return new UserSS(cli.getId(), cli.getEmail(), cli.getPassword(), cli.getRoles());
	}
}
