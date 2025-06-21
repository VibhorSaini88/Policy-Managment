package com.epam.system.policy_management_core;

import com.epam.system.policy_management_core.filter.JwtRequestFilter;
import com.epam.system.policy_management_core.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class PolicyManagementCoreApplication {

	private final MyUserDetailsService myUserDetailsService;
	private final JwtRequestFilter jwtRequestFilter;

	public static void main(String[] args) {
		SpringApplication.run(PolicyManagementCoreApplication.class, args);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		log.debug("authenticationManager start: "+config);
		System.out.println("authenticationManager start: "+config);
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.debug("filterChain start: ");
		System.out.println("filterChain start: ");
		return http
				.csrf(AbstractHttpConfigurer::disable)  // Updated way to disable CSRF
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/login").permitAll()
						.anyRequest().authenticated()
				)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.userDetailsService(myUserDetailsService)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		log.debug("passwordEncoder start: ");
		System.out.println("passwordEncoder start: ");
		return new BCryptPasswordEncoder();
	}

}
