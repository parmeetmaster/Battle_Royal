package com.yash.battleroyal.Model;

public class SliderModel {
    private String sliderId,sliderImage;

    public SliderModel(String sliderId, String sliderImage) {
        this.sliderId = sliderId;
        this.sliderImage = sliderImage;
    }

    public SliderModel() {
    }

    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }
}
