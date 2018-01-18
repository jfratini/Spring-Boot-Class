package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<Todo, Long> {

	default Optional<Todo> findById(Long id) {
		return Optional.ofNullable(findOne(id));
	}

}
