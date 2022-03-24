package com.yash.battleroyal.Model;

public class ResultModel {
    private String resultId, resultName, resultImage;

    public ResultModel(String resultId, String resultName, String resultImage) {
        this.resultId = resultId;
        this.resultName = resultName;
        this.resultImage = resultImage;
    }

    public ResultModel() {
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultImage() {
        return resultImage;
    }

    public void setResultImage(String resultImage) {
        this.resultImage = resultImage;
    }
}
