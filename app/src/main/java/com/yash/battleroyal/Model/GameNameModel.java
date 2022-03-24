package com.yash.battleroyal.Model;

public class GameNameModel {
    private String gameId, gameName, gameImage;

    public GameNameModel(String gameId, String gameName, String gameImage) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameImage = gameImage;
    }

    public GameNameModel() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }
}
