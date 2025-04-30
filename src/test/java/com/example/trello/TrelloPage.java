package com.example.trello;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class TrelloPage {

    private String baseUrl = "https://api.trello.com/1";
    private String apiKey = "API_KEY";  // API anahtarınızı buraya ekleyin
    private String token = "TOKEN";  // Tokenınızı buraya ekleyin

    // Board oluşturma
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

    // Liste alma
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

    // Bir board üzerinde liste oluşturma
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

    // Listeyi ID ile alma
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

    // Listeyi ID ile kartları alma
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

    // Kart oluşturma
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

    // Kart güncelleme
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

    // Kart silme
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

    // Board silme
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