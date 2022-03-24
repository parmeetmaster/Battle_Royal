package com.yash.battleroyal.Model;

public class TournamentModel {
    private String tournamentId, tournamentName, tournamentImage;

    public TournamentModel(String tournamentId, String tournamentName, String tournamentImage) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.tournamentImage = tournamentImage;
    }

    public TournamentModel() {
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentImage() {
        return tournamentImage;
    }

    public void setTournamentImage(String tournamentImage) {
        this.tournamentImage = tournamentImage;
    }
}
