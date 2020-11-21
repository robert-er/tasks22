package com.crud.tasks.controller;

import com.crud.tasks.dto.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnNestedServletExceptionWhenGetTasksWithWrongParameterGiven() throws Exception {
        mockMvc.perform(get("/v1/task/getTasks")).andDo(print()).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1452445", "2456875", "3454542203"})
    public void shouldGetTask(String id) {
        Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/v1/task/getTask")
                    .param("taskId", id))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotFoundException))
                    .andExpect(result -> assertEquals("Task not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"1452445", "2456875", "3454542203"})
    public void shouldDeleteTask(String id) throws Exception {
        Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(delete("/v1/task/deleteTask")
                    .param("taskId", id))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotFoundException))
                    .andExpect(result -> assertEquals("Task not found",
                            Objects.requireNonNull(result.getResolvedException()).getMessage()));
        });
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        mockMvc.perform(put("/v1/task/updateTask")
                .content(asJsonString(new TaskDto(2L, "updatedOfTheTask", "updatedOfTheTask")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("updatedOfTheTask"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("updatedOfTheTask"));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/v1/task/createTask")
                .content(asJsonString(new TaskDto(2L, "titleOfTheTask", "contentOfTheTask")))
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