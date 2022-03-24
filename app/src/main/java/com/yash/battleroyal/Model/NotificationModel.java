package com.yash.battleroyal.Model;

public class NotificationModel {
    String nId, nName;

    public NotificationModel(String nId, String nName) {
        this.nId = nId;
        this.nName = nName;
    }

    public NotificationModel() {
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }
}
