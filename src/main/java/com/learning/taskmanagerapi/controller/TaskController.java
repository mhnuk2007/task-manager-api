package com.learning.taskmanagerapi.controller;

import com.learning.taskmanagerapi.dto.TaskDto;
import com.learning.taskmanagerapi.entity.Task;
import com.learning.taskmanagerapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<TaskDto>> getTasksByBoard(@PathVariable Long boardId) {
        return new ResponseEntity<>(taskService.getTaskByBoardId(boardId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDto>> getTasksByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(taskService.getTasksByUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
