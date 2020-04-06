package com.vjpal.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.vjpal.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
}
