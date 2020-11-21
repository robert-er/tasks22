package com.crud.tasks.controller;

import com.crud.tasks.dto.TrelloCardDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTrelloBoards() throws Exception {
        mockMvc.perform(get("/v1/trello")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createTrelloCard() throws Exception {
        mockMvc.perform(post("/v1/trello")
                .content(asJsonString(new TrelloCardDto("cardName", "cardDescr",
                        "top", "5f50aa25fec549759452fd29")))
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