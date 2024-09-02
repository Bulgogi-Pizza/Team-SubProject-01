package com.example.todo_crud.controller;

import com.example.todo_crud.model.ToDo;
import com.example.todo_crud.repository.ToDoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class ToDoRestController {

  private ToDoRepository toDoRepository;

  @Autowired
  public ToDoRestController(ToDoRepository toDoRepository) {
    this.toDoRepository = toDoRepository;
  }

  @GetMapping
  public ResponseEntity<List<ToDo>> getAllToDos() {
    List<ToDo> listToDo = toDoRepository.findAll();
    return ResponseEntity.ok(listToDo);
  }

  @PostMapping
  public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
    ToDo newToDo = toDoRepository.save(toDo);
    return ResponseEntity.status(HttpStatus.CREATED).body(newToDo);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) {
    ToDo toDo = toDoRepository.findById(id).orElseThrow();
    return ResponseEntity.ok(toDo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {
    ToDo existingToDo = toDoRepository.findById(id).orElseThrow();
    existingToDo.setTitle(toDo.getTitle());
    existingToDo.setContent(toDo.getContent());
    existingToDo.setCompleted(toDo.isCompleted());
    ToDo updatedToDo = toDoRepository.save(existingToDo);
    return ResponseEntity.ok(updatedToDo);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
    toDoRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/complete")
  public ResponseEntity<ToDo> toggleComplete(@PathVariable Long id) {
    ToDo toDo = toDoRepository.findById(id).orElseThrow();
    toDo.setCompleted(!toDo.isCompleted());
    ToDo updatedToDo = toDoRepository.save(toDo);
    return ResponseEntity.ok(updatedToDo);
  }
}