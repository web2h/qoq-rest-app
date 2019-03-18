package com.web2h.qoq.rest.persistence.repository.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.web2.qoq.rest.model.entity.user.AuthenticationCode;

public interface AuthenticationCodeRepository extends CrudRepository<AuthenticationCode, Long> {

	Optional<AuthenticationCode> findByCode(String code);
}
