package com.learning.taskmanagerapi.controller;

import com.learning.taskmanagerapi.dto.TaskDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TaskWebSocketController {
    @MessageMapping("tasks/update")
    @SendTo("/topic/tasks")
    public TaskDto taskUpdate(TaskDto taskDto) {
        return taskDto;
    }

}
