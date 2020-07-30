package com.exercise.api.data.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {
  List<UserDetails> getAll();

  Optional<UserDetails> findByAuthority(String authority);
}
