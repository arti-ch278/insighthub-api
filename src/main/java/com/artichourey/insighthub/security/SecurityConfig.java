
package com.artichourey.insighthub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.artichourey.insighthub.serviceImpl.CustomUserDetailService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomUserDetailService customUserDetailService;
	private final JwtAuthenticationFilter jwtFilter;
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,  BCryptPasswordEncoder bcryptPasswordEncoder)throws Exception {
		
	AuthenticationManagerBuilder authBuilder =http.getSharedObject(AuthenticationManagerBuilder.class);
			authBuilder.userDetailsService(customUserDetailService)
	.passwordEncoder(bcryptPasswordEncoder);
	return authBuilder.build();

		
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		
		http.csrf(csrf->csrf.disable())
		.cors(cors->{})
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**","/api/users/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/api/comments/**","/posts/images/**").permitAll()
				.requestMatchers("/api/posts/**").authenticated()
				.anyRequest().authenticated());
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class );
		http.headers(header-> header.frameOptions(frame-> frame.disable()));
		return http.build();
	}

}
