package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBadges;
import com.crud.tasks.dto.CreatedTrelloCardDto;
import com.crud.tasks.dto.TrelloBoardDto;
import com.crud.tasks.dto.TrelloCardDto;
import com.crud.tasks.dto.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardsDto);
        assertEquals(1, trelloBoardsDto.size());

        trelloBoardsDto.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card_name", "card_description",
                "pos", "listId");

        CreatedTrelloCardDto createdTrelloCardDtoMock = new CreatedTrelloCardDto("1", "card_name",
                "shortUrl", new TrelloBadges());

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDtoMock);
        when(adminConfig.getAdminMail()).thenReturn("mail@dot.com");

        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", createdTrelloCardDto.getId());
        assertEquals("card_name", createdTrelloCardDto.getName());
        assertEquals("shortUrl", createdTrelloCardDto.getShortUrl());
    }
}