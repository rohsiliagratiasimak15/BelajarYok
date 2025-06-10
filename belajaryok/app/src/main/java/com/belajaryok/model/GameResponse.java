package com.belajaryok.model;


import java.util.List;

public class GameResponse {
    public boolean status;
    public String message;
    public List<Game> listGames;

    public List<Game> getListGames() {
        return listGames;
    }
}
