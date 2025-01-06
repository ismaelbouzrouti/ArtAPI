package org.enterpriseapp.artapi.security.services;

import org.enterpriseapp.artapi.users.User;
import org.enterpriseapp.artapi.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("inside load by username");

       return userRepository.findByUserName(username)
               .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
