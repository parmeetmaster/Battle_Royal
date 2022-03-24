package com.yash.battleroyal.Model;

public class MatchT2Model {
    private String matchId, matchName, matchImage;

    public MatchT2Model(String matchId, String matchName, String matchImage) {
        this.matchId = matchId;
        this.matchName = matchName;
        this.matchImage = matchImage;
    }

    public MatchT2Model() {
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchImage() {
        return matchImage;
    }

    public void setMatchImage(String matchImage) {
        this.matchImage = matchImage;
    }
}
