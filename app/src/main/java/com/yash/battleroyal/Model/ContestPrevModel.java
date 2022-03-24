package com.yash.battleroyal.Model;

public class ContestPrevModel {
    private String cId, cName, cDate, cTime, cMap, cPerspective,cWinner, cKill, cFee, cType , cSlots,cBonus;

    public ContestPrevModel(String cId, String cName, String cDate, String cTime, String cMap, String cPerspective,
                            String cWinner, String cKill, String cFee, String cType, String cSlots, String cBonus) {
        this.cId = cId;
        this.cName = cName;
        this.cDate = cDate;
        this.cTime = cTime;
        this.cMap = cMap;
        this.cPerspective = cPerspective;
        this.cWinner = cWinner;
        this.cKill = cKill;
        this.cFee = cFee;
        this.cType = cType;
        this.cSlots = cSlots;
        this.cBonus = cBonus;
    }

    public ContestPrevModel() {
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getcMap() {
        return cMap;
    }

    public void setcMap(String cMap) {
        this.cMap = cMap;
    }

    public String getcPerspective() {
        return cPerspective;
    }

    public void setcPerspective(String cPerspective) {
        this.cPerspective = cPerspective;
    }

    public String getcBonus() {
        return cBonus;
    }

    public void setcBonus(String cBonus) {
        this.cBonus = cBonus;
    }

    public String getcWinner() {
        return cWinner;
    }

    public void setcWinner(String cWinner) {
        this.cWinner = cWinner;
    }

    public String getcKill() {
        return cKill;
    }

    public void setcKill(String cKill) {
        this.cKill = cKill;
    }

    public String getcFee() {
        return cFee;
    }

    public void setcFee(String cFee) {
        this.cFee = cFee;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcSlots() {
        return cSlots;
    }

    public void setcSlots(String cSlots) {
        this.cSlots = cSlots;
    }
}
