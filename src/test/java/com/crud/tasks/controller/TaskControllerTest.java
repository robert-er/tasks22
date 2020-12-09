package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.dto.TaskDto;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void shouldReturnListWhenGetTasksPerformed() throws Exception {
        mockMvc.perform(get("/v1/task"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1452445", "2456875", "3454542203"})
    public void shouldReturnNestedServletExceptionWhenGetTask(String id) {
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/v1/task/{id}", id))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotFoundException))
                    .andExpect(result -> assertEquals("Task not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"1452445", "2456875", "3454542203"})
    public void shouldDeleteTask(String id) throws Exception {
        when(service.getTask(Long.parseLong(id)))
                .thenReturn(Optional.of(new Task(Long.parseLong(id), "test", "test")));

        mockMvc.perform(delete("/v1/task/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        TaskDto taskDto = new TaskDto(2L, "updatedOfTheTask", "updatedOfTheTask");
        when(service.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(taskMapper.mapToTask(taskDto));

        mockMvc.perform(put("/v1/task")
                .content(asJsonString(taskDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("updatedOfTheTask"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("updatedOfTheTask"));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        TaskDto taskDto = new TaskDto(2L, "titleOfTheTask", "contentOfTheTask");
        when(service.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(taskMapper.mapToTask(taskDto));

        mockMvc.perform(post("/v1/task")
                .content(asJsonString(taskDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}