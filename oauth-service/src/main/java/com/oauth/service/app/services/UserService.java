package com.oauth.service.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth.service.app.clients.UserFeignClient;
import com.user.service.commons.app.entity.UserEntity;

import brave.Tracer;

@Service
public class UserService implements  IUserService, UserDetailsService{
	
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private Tracer tracer;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userFeignClient.findByUsername(username);
		
		if(user == null) {
			log.error("Error en el login, no existe el usuario '"+ username+ "' en el sistema");
			throw new UsernameNotFoundException("Error en el login, no existe el usuario '"+ username + "' en el sistema");
		}
		
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).
				peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		log.info("Usuario autenticado: " + username);
		
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, 
				true, true, authorities);
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userFeignClient.findByUsername(username);
	}

}
