package com.temmahadi.healthcare.BackEnd;

public class OTPRequest {
    private String referenceNo;
    private String otp;
    
    @com.google.gson.annotations.SerializedName(value="subscriptionStatus", alternate={"statusCode", "status"})
    private String subscriptionStatus;

    @com.google.gson.annotations.SerializedName(value="statusDetail", alternate={"message"})
    private String statusDetail;

    // Constructor with parameters
    public OTPRequest(String referenceNo, String otp, String subscriptionStatus) {
        this.referenceNo = referenceNo;
        this.otp = otp;
        this.subscriptionStatus = subscriptionStatus;
    }


    // Getter and Setter methods
    public String getreferenceNo() {
        return referenceNo;
    }

    public void setreferenceNo(String mobileNumber) {
        this.referenceNo = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
    public String getsubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setsubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}

