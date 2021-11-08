package com.example.springfitness.service;

import com.example.springfitness.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository user;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return user.findByUsername(username);
    }
}
