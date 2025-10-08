package com.learning.taskmanagerapi.service;

import com.learning.taskmanagerapi.dto.TaskDto;
import com.learning.taskmanagerapi.entity.Board;
import com.learning.taskmanagerapi.entity.Task;
import com.learning.taskmanagerapi.entity.User;
import com.learning.taskmanagerapi.repository.BoardRepository;
import com.learning.taskmanagerapi.repository.TaskRepository;
import com.learning.taskmanagerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public TaskDto createTask(TaskDto dto) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));
        User assignedUser = dto.getAssignedToId() != null
                ? userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("User not found"))
                : null;

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .board(board)
                .assignedTo(assignedUser)
                .build();

        Task savedTask = taskRepository.save(task);
        TaskDto response = mapToDto(savedTask);

        return response;
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<TaskDto> getTaskByBoardId(Long boardId) {
        return taskRepository.findByBoardId(boardId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private TaskDto mapToDto(Task savedTask) {
        return TaskDto.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .status(savedTask.getStatus())
                .boardId(savedTask.getBoard() != null ? savedTask.getBoard().getId() : null)
                .assignedToId(savedTask.getAssignedTo() != null ? savedTask.getAssignedTo().getId() : null)
                .assignedToUsername(savedTask.getAssignedTo() != null ? savedTask.getAssignedTo().getUsername() : null)
                .build();
    }
}
