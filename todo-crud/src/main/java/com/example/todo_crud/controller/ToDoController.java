package com.example.todo_crud.controller;

import com.example.todo_crud.model.ToDo;
import com.example.todo_crud.repository.ToDoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ToDoController {

  private ToDoRepository toDoRepository;

  @Autowired
  public void ToDoController(ToDoRepository toDoRepository) {
    this.toDoRepository = toDoRepository;
  }

  @GetMapping("/")
  public String viewToDo(Model model) {
    // DB에서 ToDo 항목 가져옴
    List<ToDo> listToDo = toDoRepository.findAll();
    // 모델에 ToDo 리스트 추가
    model.addAttribute("listToDo", listToDo);
    return "index";
  }

  @GetMapping("/MVC/add")
  public String showNewToDoForm(Model model) {
    ToDo toDo = new ToDo();
    model.addAttribute("toDo", toDo);
    return "add";
  }

  @PostMapping("/MVC/save")
  public String saveToDo(@ModelAttribute("toDo") ToDo toDo) {
    toDoRepository.save(toDo);
    return "redirect:/";
  }

  @GetMapping("/MVC/edit/{id}")
  public String showEditToDoForm(@PathVariable("id") Long id, Model model) {
    ToDo toDo = toDoRepository.findById(id).get();
    model.addAttribute("toDo", toDo);
    return "edit";
  }

  @GetMapping("/MVC/delete/{id}")
  public String deleteToDo(@PathVariable("id") Long id) {
    toDoRepository.deleteById(id);
    return "redirect:/";
  }

  @GetMapping("/MVC/complete/{id}")
  public String saveToDo(@PathVariable("id") Long id) {
    ToDo toDo = toDoRepository.findById(id).get();
    toDo.setCompleted(!toDo.isCompleted());
    toDoRepository.save(toDo);
    return "redirect:/";
  }

}
