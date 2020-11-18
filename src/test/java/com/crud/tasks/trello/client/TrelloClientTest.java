package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.dto.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBadges;
import com.crud.tasks.dto.TrelloBoardDto;

import com.crud.tasks.dto.TrelloCardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @BeforeEach
    public void init() {
        when(trelloConfig.getTrelloEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
    }

    @Test
    public void getTrelloBoards() {
        //Given
        when(trelloConfig.getTrelloUsername()).thenReturn("TWOJ_USERNAME_TRELLO");
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = getUri();

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        Assertions.assertEquals(1, fetchedTrelloBoards.size());
        Assertions.assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        Assertions.assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        Assertions.assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void createNewCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test description",
                "top",
                "test_id");

        URI uri = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloEndPoint() + "/cards")
                .queryParam("key", "test")
                .queryParam("token", "test")
                .queryParam("idList", "test_id")
                .queryParam("name", "Test task")
                .queryParam("desc", "Test description")
                .queryParam("pos", "top")
                .build()
                .encode()
                .toUri();

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new TrelloBadges()
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        Assertions.assertEquals("1", newCard.getId());
        Assertions.assertEquals("Test task", newCard.getName());
        Assertions.assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() {
        //Given
        when(trelloConfig.getTrelloUsername()).thenReturn("TWOJ_USERNAME_TRELLO");
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[0];

        URI uri = getUri();

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        Assertions.assertEquals(0, fetchedTrelloBoards.size());
    }

    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloEndPoint()
                + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                .queryParam("key", "test")
                .queryParam("token", "test")
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }
}