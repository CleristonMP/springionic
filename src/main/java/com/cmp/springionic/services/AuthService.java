package com.cmp.springionic.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmp.springionic.domain.Client;
import com.cmp.springionic.repositories.ClientRepository;
import com.cmp.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		Optional<Client> obj = Optional.ofNullable(clientRepository.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("Email não encontrado")));
		Client client = obj.get();
		String newPass = newPassword();
		client.setPassword(encoder.encode(newPass));

		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
