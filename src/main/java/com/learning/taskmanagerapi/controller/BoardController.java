package com.learning.taskmanagerapi.controller;

import com.learning.taskmanagerapi.dto.BoardDto;
import com.learning.taskmanagerapi.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto){
        return new ResponseEntity<>(boardService.createBoard(boardDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardDto>> getBoardByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(boardService.getBoardByUser(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards(){
        return new ResponseEntity<>(boardService.getAllBoards(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long id){
        return new ResponseEntity<>(boardService.getBoardById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
