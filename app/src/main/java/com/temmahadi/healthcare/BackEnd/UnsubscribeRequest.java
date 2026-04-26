package com.temmahadi.healthcare.BackEnd;

import com.google.gson.annotations.SerializedName;

public class UnsubscribeRequest {
    @SerializedName("subscriberId")
    private String subscriberId;

    public UnsubscribeRequest(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }
}
