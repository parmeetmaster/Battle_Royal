package com.yash.battleroyal.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class RegistrationRequest {
    private String userId;
    private String requestedBy;
    private String requestedId;
    private String teamName;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String userId, String requestedBy, String requestedId, String teamName) {
        this.userId = userId;
        this.requestedBy = requestedBy;
        this.requestedId = requestedId;
        this.teamName = teamName;

    }

    public String getRequestedId() {
        return requestedId;
    }

    public void setRequestedId(String requestedId) {
        this.requestedId = requestedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
