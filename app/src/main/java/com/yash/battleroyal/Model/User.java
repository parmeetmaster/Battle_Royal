package com.yash.battleroyal.Model;

public class User {
    private String name, email, pass, characterID,mobileNumber,referPhno;
    private long coins = 0;
    private long bCoins = 50;
    private long tierPoint = 1;


    public User(String name, String email, String pass, String characterID, String mobileNumber, String etReferPhno) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.characterID = characterID;
        this.mobileNumber = mobileNumber;
        this.referPhno=etReferPhno;
    }

    public String getReferPhno() {
        return referPhno;
    }

    public void setReferPhno(String referPhno) {
        this.referPhno = referPhno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCharacterID() {
        return characterID;
    }

    public void setCharacterID(String characterID) {
        this.characterID = characterID;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getbCoins() {
        return bCoins;
    }

    public void setbCoins(long bCoins) {
        this.bCoins = bCoins;
    }

    public long getTierPoint() {
        return tierPoint;
    }

    public void setTierPoint(long tierPoint) {
        this.tierPoint = tierPoint;
    }
}
