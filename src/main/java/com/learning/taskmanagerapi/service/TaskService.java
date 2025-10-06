package com.learning.taskmanagerapi.service;

import com.learning.taskmanagerapi.entity.Board;
import com.learning.taskmanagerapi.entity.Task;
import com.learning.taskmanagerapi.repository.BoardRepository;
import com.learning.taskmanagerapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardRepository boardRepository;

    public List<Task> getTaskByBoardId(Long boardId) {
        return taskRepository.findByBoardId(boardId);
    }

    public Task createTask(Long boardId, Task task) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
        task.setBoard(board);

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }


}
