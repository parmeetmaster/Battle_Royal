package com.yash.battleroyal.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class TournamentRegistrationModel {
    private String userId;
    private String characterId;
    private String playerName;
    private String teamName;

    public TournamentRegistrationModel(String userId, String characterId, String playerName, String teamName) {
        this.userId = userId;
        this.characterId = characterId;
        this.playerName = playerName;
        this.teamName = teamName;
    }

    public TournamentRegistrationModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @ServerTimestamp
    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
