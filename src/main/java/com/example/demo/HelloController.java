package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public Map<String, String> sayHello() {
		Map<String, String> map = new HashMap<>();
		map.put("messsage", "Hello world");
		return map;
	}

	@GetMapping("/hello/{who}")
	public Map<String, String> sayHello(@PathVariable String who) {
		Map<String, String> map = new HashMap<>();
		String message = String.format("Hello %s", who);
		map.put("messsage", message);
		map.put("color", "blue");
		return map;
	}

}
