package com.glaucustask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    @SerializedName("tableEmailValidate")
    private boolean tableEmailValidate;
    @Expose
    @SerializedName("tableEmailEmailAddress")
    private String tableEmailEmailAddress;

    @Expose
    @SerializedName("idtableEmail")
    private int idtableEmail;

    public Data(boolean tableEmailValidate, String tableEmailEmailAddress) {
        this.tableEmailValidate = tableEmailValidate;
        this.tableEmailEmailAddress = tableEmailEmailAddress;
    }

    public boolean getTableEmailValidate() {
        return tableEmailValidate;
    }

    public void setTableEmailValidate(boolean tableEmailValidate) {
        this.tableEmailValidate = tableEmailValidate;
    }

    public String getTableEmailEmailAddress() {
        return tableEmailEmailAddress;
    }

    public void setTableEmailEmailAddress(String tableEmailEmailAddress) {
        this.tableEmailEmailAddress = tableEmailEmailAddress;
    }

    public int getIdtableEmail() {
        return idtableEmail;
    }

    public void setIdtableEmail(int idtableEmail) {
        this.idtableEmail = idtableEmail;
    }
}
