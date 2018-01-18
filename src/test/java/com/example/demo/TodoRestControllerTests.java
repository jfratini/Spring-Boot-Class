package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TodoRestControllerTests {

  @Autowired
  private TestRestTemplate template;

  @MockBean 
  private TodoJpaRepository repository;
  private Todo todo1;

  @Before
  public void setup() {
    List<Todo> todos = new ArrayList<>();
    todo1 = Todo.builder().title("Todo1").id(1L).build();
    todos.add(todo1);
    Todo todo2 = Todo.builder().title("Todo2").id(2L).build();
    todos.add(todo2);
    when(repository.findAll()).thenReturn(todos);

    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    when(repository.findById(1L)).thenReturn(Optional.of(todo1));
    when(repository.findById(2L)).thenReturn(Optional.of(todo2));

    when(repository.save(any(Todo.class))).thenReturn(todo1);
  }

  @Test
  public void shouldReturnTodos() {
    ResponseEntity<List<Todo>> response = template.exchange("/api/todos", HttpMethod.GET,
        null, new ParameterizedTypeReference<List<Todo>>() {});
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<Todo> todos = response.getBody();
    assertThat(todos.size()).isEqualTo(2);
  }

  @Test
  public void shouldReturnASingleTodo() {
    ResponseEntity<Todo> response = template.getForEntity("/api/todos/1", Todo.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Todo todo = response.getBody();
    assertThat(todo.getTitle()).isEqualTo("Todo1");
  }

  @Test
  public void shouldNotFindTodoId3() {
    ResponseEntity<Todo> response = template.getForEntity("/api/todos/3", Todo.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void shouldSaveATodo() {
    ResponseEntity<Todo> response = template.postForEntity("/api/todos", todo1, Todo.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Todo todo = response.getBody();
    assertThat(todo.getTitle()).isEqualTo("Todo1");
  }

  @Test
  public void shouldDeleteATodo() {
    ResponseEntity<Todo> response =
        template.exchange("/api/todos/1", HttpMethod.DELETE, null, Todo.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

}