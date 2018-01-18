package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {

  private final TodoJpaRepository todoRepository;

  public TodoController(TodoJpaRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @GetMapping("/todos")
  public String fetchTodos(Model model) {

    List<TodoViewModel> todoViews = todoRepository
        .findAll()
        .stream()
        .map(TodoViewModel::new)
        .collect(Collectors.toList());

    model.addAttribute("todos", todoViews);

    return "todos"; // the name of the template to render
  }
}