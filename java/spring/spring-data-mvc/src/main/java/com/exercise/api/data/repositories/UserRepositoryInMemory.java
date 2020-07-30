package com.exercise.api.data.repositories;

import com.exercise.api.data.domain.User;
import com.exercise.api.data.security.Authorities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryInMemory implements UserRepository{

  private Map<String, UserDetails> database;

  public UserRepositoryInMemory(){
    database = new HashMap<>();

    UserDetails apiUser = User.builder()
                              .username("user1")
                              .password(new BCryptPasswordEncoder().encode("pass1"))
                              .token("mytoken1")
                              .authority(new SimpleGrantedAuthority(Authorities.API))
                              .build();

    UserDetails frontUser = User.builder()
                                .username("user2")
                                .password(new BCryptPasswordEncoder().encode("pass2"))
                                .token("mytoken2")
                                .authority(new SimpleGrantedAuthority(Authorities.FRONTEND))
                                .build();

    database.put(Authorities.API, apiUser);
    database.put(Authorities.FRONTEND, frontUser);
  }

  @Override
  public List<UserDetails> getAll() {
    return new ArrayList<>(database.values());
  }

  @Override
  public Optional<UserDetails> findByAuthority(String authority) {
    return Optional.of(database.get(authority));
  }
}
