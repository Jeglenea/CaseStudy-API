package com.example.trello;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import java.util.Random;

public class TrelloAutomationTest {

    TrelloPage trelloPage = new TrelloPage();

    @Test
    public void testCreateBoardAndCards() {
        // Board oluşturma
        Response boardResponse = trelloPage.createBoard("Test Board");
        System.out.println("BOARD CREATION - Response Status: " + boardResponse.getStatusCode());
        System.out.println("BOARD CREATION - Response Body: " + boardResponse.asString());
        String boardId = boardResponse.jsonPath().getString("id");
        System.out.println("BOARD CREATION - Board ID: " + boardId);
        System.out.println("---------------------------");

        // Board'a ait listeleri alma
        Response listsResponse = trelloPage.getListsOnBoard(boardId);
        System.out.println("LISTS ON BOARD - Response Status: " + listsResponse.getStatusCode());
        System.out.println("LISTS ON BOARD - Response Body: " + listsResponse.asString());
        System.out.println("---------------------------");

        // Liste oluşturma
        Response listResponse = trelloPage.createListOnBoard(boardId, "New List");
        System.out.println("LIST CREATION - Response Status: " + listResponse.getStatusCode());
        System.out.println("LIST CREATION - Response Body: " + listResponse.asString());
        String listId = listResponse.jsonPath().getString("id");
        System.out.println("LIST CREATION - Created List ID: " + listId);
        System.out.println("---------------------------");

        // Liste oluşturulduğu ve boş olduğu doğrulanır
        Response getListResponse = trelloPage.getListById(listId);
        System.out.println("GET LIST BY ID - Response Status: " + getListResponse.getStatusCode());
        System.out.println("GET LIST BY ID - Response Body: " + getListResponse.asString());

        Response getEmptyListResponse = trelloPage.getCardsByListId(listId);
        System.out.println("GET CARDS BY LIST ID (EMPTY) - Response Status: " + getEmptyListResponse.getStatusCode());
        System.out.println("GET CARDS BY LIST ID (EMPTY) - Response Body: " + getEmptyListResponse.asString());
        System.out.println("---------------------------");

        // Kart oluşturma ve oluştuğunun kontrolü
        Response cardResponse1 = trelloPage.createCard(listId, "Card 1");
        System.out.println("CARD 1 CREATION - Response Status: " + cardResponse1.getStatusCode());
        System.out.println("CARD 1 CREATION - Response Body: " + cardResponse1.asString());
        String cardId1 = cardResponse1.jsonPath().getString("id");

        Response cardResponse2 = trelloPage.createCard(listId, "Card 2");
        System.out.println("CARD 2 CREATION - Response Status: " + cardResponse2.getStatusCode());
        System.out.println("CARD 2 CREATION - Response Body: " + cardResponse2.asString());
        String cardId2 = cardResponse2.jsonPath().getString("id");

        Response getCardResponse1 = trelloPage.getCardsByListId(listId);
        System.out.println("GET CARDS BY LIST ID - Response Status: " + getCardResponse1.getStatusCode());
        System.out.println("GET CARDS BY LIST ID - Response Body: " + getCardResponse1.asString());
        System.out.println("---------------------------");

        // Kartlardan birini rastgele güncelleme
        Random rand = new Random();
        String cardToUpdate = rand.nextBoolean() ? cardId1 : cardId2;
        String newCardName = "Updated Card";
        Response updateResponse = trelloPage.updateCard(cardToUpdate, newCardName);
        System.out.println("CARD UPDATE - Response Status: " + updateResponse.getStatusCode());
        System.out.println("CARD UPDATE - Response Body: " + updateResponse.asString());
        System.out.println("---------------------------");

        // Kartları silme
        Response deleteCardResponse1 = trelloPage.deleteCard(cardId1);
        System.out.println("DELETE CARD 1 - Response Status: " + deleteCardResponse1.getStatusCode());
        System.out.println("DELETE CARD 1 - Response Body: " + deleteCardResponse1.asString());
        System.out.println("---------------------------");

        Response deleteCardResponse2 = trelloPage.deleteCard(cardId2);
        System.out.println("DELETE CARD 2 - Response Status: " + deleteCardResponse2.getStatusCode());
        System.out.println("DELETE CARD 2 - Response Body: " + deleteCardResponse2.asString());
        System.out.println("---------------------------");

        // Board'u silme
        Response deleteBoardResponse = trelloPage.deleteBoard(boardId);
        System.out.println("DELETE BOARD - Response Status: " + deleteBoardResponse.getStatusCode());
        System.out.println("DELETE BOARD - Response Body: " + deleteBoardResponse.asString());
    }
}