package com.crud.tasks.service;

import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTest {

    @Autowired
    private DbService service;

    @Test
    public void shouldGetAllTasks() throws TaskNotFoundException {
        //Given
        Task task1 = new Task(1L, "task_name", "task_description");
        Task savedTask1 = service.saveTask(task1);

        Task task2 = new Task(2L, "task_name2", "task_description2");
        Task savedTask2 = service.saveTask(task2);
        //When
        List<Task> tasks = service.getAllTasks();
        //Then
        assertTrue(tasks.contains(savedTask1));
        assertTrue(tasks.contains(savedTask2));
        //CleanUp
        service.deleteTask(savedTask1.getId());
        service.deleteTask(savedTask2.getId());

    }

    @Test
    public void shouldGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "task_name8", "task_description8");
        Task savedTask = service.saveTask(task);
        //When
        Optional<Task> retrievedTask = service.getTask(savedTask.getId());
        //Then
        assertNotEquals(Optional.empty(), retrievedTask);
        //CleanUp
        service.deleteTask(savedTask.getId());
    }

    @Test
    public void shouldSaveTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "task_name5", "task_description5");
        //When
        Task savedTask = service.saveTask(task);
        //Then
        assertNotNull(savedTask.getId());
        assertEquals("task_name5", savedTask.getTitle());
        assertEquals("task_description5", savedTask.getContent());
        //CleanUp
        service.deleteTask(savedTask.getId());
    }

    @Test
    public void shouldDeleteTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "task_name45", "task_description45");
        Task savedTask = service.saveTask(task);
        //When
        service.deleteTask(savedTask.getId());
        //Then
        assertEquals(Optional.empty(), service.getTask(savedTask.getId()));
    }
}