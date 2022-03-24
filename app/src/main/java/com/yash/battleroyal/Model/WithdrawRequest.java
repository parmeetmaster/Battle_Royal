package com.yash.battleroyal.Model;


import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {
    private String userId;
    private String paytmNumber;
    private String requestedBy;
    private String coins;

    public WithdrawRequest() {

    }

    public WithdrawRequest(String userId, String paytmNumber, String requestedBy,String coins) {
        this.userId = userId;
        this.paytmNumber = paytmNumber;
        this.requestedBy = requestedBy;
        this.coins = coins;
    }

    public String getUserId() {
        return userId;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public void setPaytmNumber(String paytmNumber) {
        this.paytmNumber = paytmNumber;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    @ServerTimestamp
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

