package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.dto.TrelloBoardDto;
import com.crud.tasks.dto.TrelloCardDto;
import com.crud.tasks.dto.TrelloListDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void mapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("ID-BOARD-1", "Board1", new ArrayList<>());
        TrelloListDto trelloListDto1 = new TrelloListDto("ID-LIST-1", "List1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("ID-LIST-2", "List2", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("ID-LIST-3", "List3", false);
        trelloBoardDto1.getLists().add(trelloListDto1);
        trelloBoardDto1.getLists().add(trelloListDto2);
        trelloBoardDto1.getLists().add(trelloListDto3);

        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("ID-BOARD-2", "Board2", new ArrayList<>());
        TrelloListDto trelloListDto4 = new TrelloListDto("ID-LIST-4", "List4", false);
        TrelloListDto trelloListDto5 = new TrelloListDto("ID-LIST-5", "List5", false);
        TrelloListDto trelloListDto6 = new TrelloListDto("ID-LIST-6", "List6", false);
        trelloBoardDto2.getLists().add(trelloListDto4);
        trelloBoardDto2.getLists().add(trelloListDto5);
        trelloBoardDto2.getLists().add(trelloListDto6);

        TrelloBoardDto trelloBoardDto3 = new TrelloBoardDto("ID-BOARD-3", "Board3", new ArrayList<>());
        TrelloListDto trelloListDto7 = new TrelloListDto("ID-LIST-7", "List7", false);
        TrelloListDto trelloListDto8 = new TrelloListDto("ID-LIST-8", "List8", false);
        TrelloListDto trelloListDto9 = new TrelloListDto("ID-LIST-9", "List9", false);
        trelloBoardDto3.getLists().add(trelloListDto7);
        trelloBoardDto3.getLists().add(trelloListDto8);
        trelloBoardDto3.getLists().add(trelloListDto9);

        trelloBoardsDto.add(trelloBoardDto1);
        trelloBoardsDto.add(trelloBoardDto2);
        trelloBoardsDto.add(trelloBoardDto3);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        assertEquals(3, trelloBoards.size());
        assertEquals(9, trelloBoards.stream().mapToLong(board -> board.getLists().size()).sum());
    }

    @Test
    void mapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();

        TrelloBoard trelloBoard1 = new TrelloBoard("ID-BOARD-1", "Board1", new ArrayList<>());
        TrelloList trelloList1 = new TrelloList("ID-LIST-1", "List1", false);
        TrelloList trelloList2 = new TrelloList("ID-LIST-2", "List2", false);
        TrelloList trelloList3 = new TrelloList("ID-LIST-3", "List3", false);
        trelloBoard1.getLists().add(trelloList1);
        trelloBoard1.getLists().add(trelloList2);
        trelloBoard1.getLists().add(trelloList3);

        TrelloBoard trelloBoard2 = new TrelloBoard("ID-BOARD-2", "Board2", new ArrayList<>());
        TrelloList trelloList4 = new TrelloList("ID-LIST-4", "List4", false);
        TrelloList trelloList5 = new TrelloList("ID-LIST-5", "List5", false);
        TrelloList trelloList6 = new TrelloList("ID-LIST-6", "List6", false);
        trelloBoard2.getLists().add(trelloList4);
        trelloBoard2.getLists().add(trelloList5);
        trelloBoard2.getLists().add(trelloList6);

        TrelloBoard trelloBoard3 = new TrelloBoard("ID-BOARD-3", "Board3", new ArrayList<>());
        TrelloList trelloList7 = new TrelloList("ID-LIST-7", "List7", false);
        TrelloList trelloList8 = new TrelloList("ID-LIST-8", "List8", false);
        TrelloList trelloList9 = new TrelloList("ID-LIST-9", "List9", false);
        trelloBoard3.getLists().add(trelloList7);
        trelloBoard3.getLists().add(trelloList8);
        trelloBoard3.getLists().add(trelloList9);

        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        trelloBoards.add(trelloBoard3);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(3, trelloBoardsDto.size());
        assertEquals(9, trelloBoardsDto.stream().mapToLong(board -> board.getLists().size()).sum());
    }

    @Test
    void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name",
                "description",
                "pos",
                "listId");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }

    @Test
    void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name",
                "description",
                "pos",
                "listId");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }
}