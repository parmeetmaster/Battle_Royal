package com.yash.battleroyal.Model;

public class CrewModel {
    private String CrewName,Crew1, Crew2, Crew3, Crew4, Crew5;

    public CrewModel(String crewName,String crew1, String crew2, String crew3, String crew4, String crew5) {
        CrewName = crewName;
        Crew1 = crew1;
        Crew2 = crew2;
        Crew3 = crew3;
        Crew4 = crew4;
        Crew5 = crew5;
    }

    public CrewModel() {
    }

    public String getCrewName() {
        return CrewName;
    }

    public void setCrewName(String crewName) {
        CrewName = crewName;
    }

    public String getCrew1() {
        return Crew1;
    }

    public void setCrew1(String crew1) {
        Crew1 = crew1;
    }

    public String getCrew2() {
        return Crew2;
    }

    public void setCrew2(String crew2) {
        Crew2 = crew2;
    }

    public String getCrew3() {
        return Crew3;
    }

    public void setCrew3(String crew3) {
        Crew3 = crew3;
    }

    public String getCrew4() {
        return Crew4;
    }

    public void setCrew4(String crew4) {
        Crew4 = crew4;
    }

    public String getCrew5() {
        return Crew5;
    }

    public void setCrew5(String crew5) {
        Crew5 = crew5;
    }
}
