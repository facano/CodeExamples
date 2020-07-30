package com.exercise.api.data.services;

import com.exercise.api.data.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<UserDetails> getAll(){
    return userRepository.getAll();
  }

  public UserDetails findByAuthority(String authority){
    return userRepository.findByAuthority(authority).get();
  }
}
