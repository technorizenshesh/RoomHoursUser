package com.example.roomhoursuser.CheckInScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeDetailsModel {

    @SerializedName("result")
    @Expose
    private HomeDetailsDataModel result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public HomeDetailsDataModel getResult() {
        return result;
    }

    public void setResult(HomeDetailsDataModel result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
