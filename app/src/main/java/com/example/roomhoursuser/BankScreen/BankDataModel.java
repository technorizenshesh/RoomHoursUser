package com.example.roomhoursuser.BankScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDataModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("AccounHolderName")
    @Expose
    private String accounHolderName;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("Bank_Account_Number")
    @Expose
    private String bankAccountNumber;
    @SerializedName("Bank_CODE")
    @Expose
    private String bankCODE;
    @SerializedName("area_code")
    @Expose
    private String areaCode;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccounHolderName() {
        return accounHolderName;
    }

    public void setAccounHolderName(String accounHolderName) {
        this.accounHolderName = accounHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankCODE() {
        return bankCODE;
    }

    public void setBankCODE(String bankCODE) {
        this.bankCODE = bankCODE;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
