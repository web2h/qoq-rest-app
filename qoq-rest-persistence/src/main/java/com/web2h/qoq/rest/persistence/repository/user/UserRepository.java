package com.web2h.qoq.rest.persistence.repository.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.web2.qoq.rest.model.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByAuthenticationToken(String authenticationToken);

	Optional<User> findByCellPhoneNumber(String cellPhoneNumber);

	Optional<User> findByEmail(String email);
}