package com.cs.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.config.DbContextHolder;
import com.cs.config.DbType;
import com.cs.domain.User;
import com.cs.service.UserService;
import com.cs.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/admin")
	public ResponseEntity<List<User>> getAllUsersByAdmin(Pageable pageable) {
		try {
			DbContextHolder.setDbType(DbType.dbadmin);
			final Page<User> page = userService.getAllManagedUsers(pageable);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		} finally {
			DbContextHolder.clearDbType();
		}
	}

	@GetMapping("/users/user")
	public ResponseEntity<List<User>> getAllUsersByUser(Pageable pageable) {
		try {
			DbContextHolder.setDbType(DbType.dbuser);
			final Page<User> page = userService.getAllManagedUsers(pageable);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		} finally {
			DbContextHolder.clearDbType();
		}
	}

}
