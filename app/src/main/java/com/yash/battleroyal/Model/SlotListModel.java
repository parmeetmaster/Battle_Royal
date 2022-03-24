package com.yash.battleroyal.Model;

public class SlotListModel {
    private String slotId, slotName, slotImage;

    public SlotListModel(String slotId, String slotName, String slotImage) {
        this.slotId = slotId;
        this.slotName = slotName;
        this.slotImage = slotImage;
    }

    public SlotListModel() {
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getSlotImage() {
        return slotImage;
    }

    public void setSlotImage(String slotImage) {
        this.slotImage = slotImage;
    }
}
