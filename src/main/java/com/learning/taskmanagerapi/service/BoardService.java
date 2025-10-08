package com.learning.taskmanagerapi.service;

import com.learning.taskmanagerapi.dto.BoardDto;
import com.learning.taskmanagerapi.dto.TaskDto;
import com.learning.taskmanagerapi.entity.Board;
import com.learning.taskmanagerapi.entity.Task;
import com.learning.taskmanagerapi.entity.User;
import com.learning.taskmanagerapi.repository.BoardRepository;
import com.learning.taskmanagerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public BoardDto createBoard(BoardDto dto) {
        User user = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Board board = Board.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdBy(user)
                .build();

        Board savedBoard = boardRepository.save(board);
        return mapToDto(savedBoard);
    }

    public List<BoardDto> getBoardByUser(Long userId) {
        return boardRepository.findByCreatedById(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());


    }

    public List<BoardDto> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BoardDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + id));
        return mapToDto(board);
    }


    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }


    private BoardDto mapToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .createdById(board.getCreatedBy() != null ? board.getCreatedBy().getId() : null)
                .createdByUsername(board.getCreatedBy() != null ? board.getCreatedBy().getUsername() : null)
                .tasks(board.getTasks() != null ? board.getTasks().stream().map(this::mapTaskToDto).collect(Collectors.toList()) : null)
                .build();
    }

    private TaskDto mapTaskToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .boardId(task.getBoard() != null ? task.getBoard().getId() : null)
                .assignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)
                .assignedToUsername(task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : null)
                .build();
    }
}
