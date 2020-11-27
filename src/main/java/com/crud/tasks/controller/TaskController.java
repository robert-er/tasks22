package com.crud.tasks.controller;

import com.crud.tasks.dto.TaskDto;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/task")
public class TaskController {

    private final TaskMapper taskMapper;
    private final DbService service;

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @GetMapping("{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
      return taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @DeleteMapping("{taskId}")
    public void deleteTask(@PathVariable Long taskId) throws TaskNotFoundException {
            service.deleteTask(taskId);
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @PostMapping
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }
}
