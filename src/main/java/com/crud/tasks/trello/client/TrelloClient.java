package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloEndPoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    @Value("${trello.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = getUri();
        TrelloBoardDto[] boardResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
        return Optional.ofNullable(boardResponse).map(Arrays::asList).orElseGet(ArrayList::new);
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloEndPoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("idList", trelloCardDto.getListId())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .build()
                .encode()
                .toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloEndPoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key",trelloAppKey)
                .queryParam("token", trelloAppToken )
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }

}
