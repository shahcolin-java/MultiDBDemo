package com.cs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Page<User> findAll(Pageable pageable);

}
