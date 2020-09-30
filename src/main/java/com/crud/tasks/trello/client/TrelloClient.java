package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
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

//        if (boardResponse != null) {
//            return Arrays.asList(boardResponse);
//        }
//        return new ArrayList<>();

        return Optional.ofNullable(boardResponse).map(Arrays::asList).orElseGet(ArrayList::new);

    }

    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloEndPoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key",trelloAppKey)
                .queryParam("token", trelloAppToken )
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();
    }

}
