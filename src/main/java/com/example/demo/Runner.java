package com.example.demo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(Runner.class);

	private final HelloService helloService;
	private final TodoJpaRepository todoRepository;

	public Runner(HelloService helloService, TodoJpaRepository todoRepository) {
		this.helloService = helloService;
		this.todoRepository = todoRepository;
		logger.debug("exiting run method");
	}

	@Override
	public void run(String... args) {
		LocalDate nextWeek = LocalDate.now().plus(7, ChronoUnit.DAYS);

		if (todoRepository.count() == 0) {
			logger.debug("todo repo is empty, adding data...");
			todoRepository.save(Todo.builder().title("Shop").description("Go shopping ahead of the fishing trip")
					.dueDate(nextWeek).build());
			todoRepository.save(
					Todo.builder().title("Pack").description("Be sure to pack your things").dueDate(nextWeek).build());
			todoRepository
					.save(Todo.builder().title("Drive").description("Drive to the airport").dueDate(nextWeek).build());
			todoRepository.save(Todo.builder().title("Fly").description("Fly to some mysterious destination")
					.dueDate(nextWeek).build());
		}

		logger.debug("exiting run method..");

	}

}
