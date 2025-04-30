package com.example.trello;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class TrelloPage {

    private String baseUrl = "https://api.trello.com/1";
    private String apiKey = "API_KEY";  // Add your API_KEY here
    private String token = "TOKEN";  //Add your TOKEN here.

    // Create board
    public Response createBoard(String name) {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .queryParam("name", name)
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .post(baseUrl + "/boards")
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "createBoard failed with status " + response.getStatusCode();
        return response;
    }

    // Get lists on board
    public Response getListsOnBoard(String boardId) {
        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .header("Accept", "application/json")
                .get(baseUrl + "/boards/" + boardId + "/lists")
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "getListsOnBoard failed with status " + response.getStatusCode();
        return response;
    }

    // Create list on board
    public Response createListOnBoard(String boardId, String name) {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .queryParam("name", name)
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .post(baseUrl + "/boards/" + boardId + "/lists")
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "createListOnBoard failed with status " + response.getStatusCode();
        return response;
    }

    // Get list by ID
    public Response getListById(String listId) {
        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get(baseUrl + "/lists/" + listId)
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "getListById failed with status " + response.getStatusCode();
        return response;
    }

    // Get cards by list ID
    public Response getCardsByListId(String listId) {
        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .header("Accept", "application/json")
                .get(baseUrl + "/lists/" + listId + "/cards")
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "getCardsByListId failed with status " + response.getStatusCode();

        // Check if the response is an empty array
        if (response.getBody().asString().equals("[]")) {
            System.out.println("Board List doesn't have cards in the list");
        } else {
            System.out.println("Board List has cards in the list");
        }

        return response;
    }

    // Create card
    public Response createCard(String listId, String name) {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .queryParam("name", name)
                .queryParam("idList", listId)
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .when()
                .post(baseUrl + "/cards")
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "createCard failed with status " + response.getStatusCode();
        return response;
    }

    // Update card
    public Response updateCard(String cardId, String name) {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .queryParam("name", name)
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .when()
                .put(baseUrl + "/cards/" + cardId)
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "updateCard failed with status " + response.getStatusCode();
        return response;
    }

    // Delete card
    public Response deleteCard(String cardId) {
        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .when()
                .delete(baseUrl + "/cards/" + cardId)
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "deleteCard failed with status " + response.getStatusCode();
        return response;
    }

    // Delete board
    public Response deleteBoard(String boardId) {
        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .when()
                .delete(baseUrl + "/boards/" + boardId)
                .then().extract().response();
        assert response.getStatusCode() >= 200 && response.getStatusCode() < 300 : "deleteBoard failed with status " + response.getStatusCode();
        return response;
    }



}