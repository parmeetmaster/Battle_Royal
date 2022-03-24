package com.yash.battleroyal.Model;

public class FixtureModel {
    private String fixId, fixName, fixImage;

    public FixtureModel(String fixId, String fixName, String fixImage) {
        this.fixId = fixId;
        this.fixName = fixName;
        this.fixImage = fixImage;
    }

    public FixtureModel() {
    }

    public String getFixId() {
        return fixId;
    }

    public void setFixId(String fixId) {
        this.fixId = fixId;
    }

    public String getFixName() {
        return fixName;
    }

    public void setFixName(String fixName) {
        this.fixName = fixName;
    }

    public String getFixImage() {
        return fixImage;
    }

    public void setFixImage(String fixImage) {
        this.fixImage = fixImage;
    }
}
