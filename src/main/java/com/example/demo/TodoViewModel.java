package com.example.demo;

import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class TodoViewModel {
  private final String title, description;
  private final String dueDateFormatted;

  public TodoViewModel(Todo todo) { 
    this.title = todo.getTitle();
    this.description = todo.getDescription();
    this.dueDateFormatted = todo.getDueDate().format(DateTimeFormatter.ISO_DATE);
  }
}