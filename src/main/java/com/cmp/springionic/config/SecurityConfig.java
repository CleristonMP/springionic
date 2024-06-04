package com.cmp.springionic.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
    private Environment env;
	
	@Autowired
	private JTWAuthenticationFilter jwtFilter;

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/auth/login" };

	private static final String[] PUBLIC_MATCHERS_GET = { "/products/**", "/categories/**" };

	private static final String[] PUBLIC_MATCHERS_POST = { "/clients/**" };

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers((headers) -> headers.frameOptions(withDefaults()).disable());
        }
		
		http.cors(withDefaults()).csrf((csrf) -> csrf.disable());
		http.authorizeHttpRequests(
				(authz) -> authz.requestMatchers(PUBLIC_MATCHERS).permitAll()
				.requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
				.anyRequest().authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(withDefaults());
		http.sessionManagement(
				(sessionManagement) -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}
