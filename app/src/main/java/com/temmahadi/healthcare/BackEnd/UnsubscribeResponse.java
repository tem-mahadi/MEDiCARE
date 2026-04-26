package com.temmahadi.healthcare.BackEnd;

import com.google.gson.annotations.SerializedName;

public class UnsubscribeResponse {

    @SerializedName("statusCode")
    private String statusCode;

    @SerializedName("statusDetail")
    private String statusDetail;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }
}
