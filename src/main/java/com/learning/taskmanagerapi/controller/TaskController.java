package com.learning.taskmanagerapi.controller;

import com.learning.taskmanagerapi.entity.Task;
import com.learning.taskmanagerapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Long boardId, @RequestBody Task task) {
        return new ResponseEntity<>(taskService.createTask(boardId, task), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasksByBoardId(@PathVariable Long boardId) {
        return new ResponseEntity<>(taskService.getTaskByBoardId(boardId), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
