package com.cmp.springionic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cmp.springionic.domain.Address;
import com.cmp.springionic.domain.Category;
import com.cmp.springionic.domain.City;
import com.cmp.springionic.domain.Client;
import com.cmp.springionic.domain.Product;
import com.cmp.springionic.domain.State;
import com.cmp.springionic.domain.enums.ClientType;
import com.cmp.springionic.repositories.AddressRepository;
import com.cmp.springionic.repositories.CategoryRepository;
import com.cmp.springionic.repositories.CityRepository;
import com.cmp.springionic.repositories.ClientRepository;
import com.cmp.springionic.repositories.ProductRepository;
import com.cmp.springionic.repositories.StateRepository;

@SpringBootApplication
public class SpringionicApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringionicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

		State stt1 = new State(null, "Minas Gerias");
		State stt2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", stt1);
		City c2 = new City(null, "São Paulo", stt2);
		City c3 = new City(null, "Campinas", stt2);

		stt1.getCities().addAll(Arrays.asList(c1));
		stt2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(stt1, stt2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36325415377", ClientType.INDIVIDUAL);
		cli1.getPhones().addAll(Arrays.asList("27568945", "98978756321"));

		Address a1 = new Address(null, "Rua Flores", "300", "Apt 303", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address(null, "Av. Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getAddress().addAll(Arrays.asList(a1, a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		
		addressRepository.saveAll(Arrays.asList(a1, a2));
	}
}