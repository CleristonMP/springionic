package com.cmp.springionic.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmp.springionic.domain.Address;
import com.cmp.springionic.domain.Category;
import com.cmp.springionic.domain.City;
import com.cmp.springionic.domain.Client;
import com.cmp.springionic.domain.Order;
import com.cmp.springionic.domain.OrderItem;
import com.cmp.springionic.domain.Payment;
import com.cmp.springionic.domain.PaymentByBankSlip;
import com.cmp.springionic.domain.PaymentByCard;
import com.cmp.springionic.domain.Product;
import com.cmp.springionic.domain.State;
import com.cmp.springionic.domain.enums.ClientType;
import com.cmp.springionic.domain.enums.PaymentStatus;
import com.cmp.springionic.domain.enums.Role;
import com.cmp.springionic.repositories.AddressRepository;
import com.cmp.springionic.repositories.CategoryRepository;
import com.cmp.springionic.repositories.CityRepository;
import com.cmp.springionic.repositories.ClientRepository;
import com.cmp.springionic.repositories.OrderItemRepository;
import com.cmp.springionic.repositories.OrderRepository;
import com.cmp.springionic.repositories.PaymentRepository;
import com.cmp.springionic.repositories.ProductRepository;
import com.cmp.springionic.repositories.StateRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Value("${default.recipient}")
	private String recipient;

	public void instantiateTestDatabase() throws ParseException {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama, mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de Escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		State stt1 = new State(null, "Minas Gerias");
		State stt2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", stt1);
		City c2 = new City(null, "São Paulo", stt2);
		City c3 = new City(null, "Campinas", stt2);

		stt1.getCities().addAll(Arrays.asList(c1));
		stt2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(stt1, stt2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", recipient, "36325415377", ClientType.INDIVIDUAL, encoder.encode("123"));
		cli1.getPhones().addAll(Arrays.asList("27568945", "98978756321"));

		Client cli2 = new Client(null, "Ana Costa", "ana@gmail.com", "31628382740", ClientType.INDIVIDUAL, encoder.encode("123"));
		cli2.addRole(Role.ADMIN);
		cli2.getPhones().addAll(Arrays.asList("93883321", "34252625"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apt 303", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address(null, "Av. Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Address a3 = new Address(null, "Av. Floriano", "2106", null, "Centro", "281777012", cli2, c2);

		cli1.getAddress().addAll(Arrays.asList(a1, a2));
		cli2.getAddress().addAll(Arrays.asList(a3));

		clientRepository.saveAll(Arrays.asList(cli1, cli2));
		addressRepository.saveAll(Arrays.asList(a1, a2, a3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Order o1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order o2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, a2);

		Payment pay1 = new PaymentByCard(null, PaymentStatus.SETTLED, o1, 6);
		o1.setPayment(pay1);

		Payment pay2 = new PaymentByBankSlip(null, PaymentStatus.PENDING, o2, sdf.parse("20/10/2017 00:00"), null);
		o2.setPayment(pay2);

		cli1.getOrders().addAll(Arrays.asList(o1, o2));

		orderRepository.saveAll(Arrays.asList(o1, o2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));

		OrderItem oi1 = new OrderItem(o1, p1, 0.00, 1, 2000.0);
		OrderItem oi2 = new OrderItem(o1, p3, 0.00, 2, 80.0);
		OrderItem oi3 = new OrderItem(o2, p2, 100.0, 1, 800.0);

		o1.getItems().addAll(Arrays.asList(oi1, oi2));
		o2.getItems().addAll(Arrays.asList(oi3));

		p1.getItems().addAll(Arrays.asList(oi1));
		p2.getItems().addAll(Arrays.asList(oi3));
		p3.getItems().addAll(Arrays.asList(oi2));

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
	}
}
