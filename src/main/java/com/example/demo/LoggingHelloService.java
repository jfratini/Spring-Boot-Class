package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggingHelloService implements HelloService {

	private final Logger logger = LoggerFactory.getLogger(LoggingHelloService.class);

	private final String greeting;

	public LoggingHelloService(@Value("${hello.greeting:Hello}") String greeting) {
		this.greeting = greeting;
	}

	@Override
	public void greet() {
		logger.info("{} world", greeting);

	}

}
