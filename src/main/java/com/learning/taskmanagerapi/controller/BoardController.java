package com.learning.taskmanagerapi.controller;

import com.learning.taskmanagerapi.entity.Board;
import com.learning.taskmanagerapi.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards(){
        return new ResponseEntity<>(boardService.getAllBoards(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board){
        return new ResponseEntity<>(boardService.createBoard(board), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id){
        return new ResponseEntity<>(boardService.getBoardById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
