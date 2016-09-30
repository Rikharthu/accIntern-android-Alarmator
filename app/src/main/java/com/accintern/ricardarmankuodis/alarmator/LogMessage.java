package com.accintern.ricardarmankuodis.alarmator;

/**
 * Created by ricard.arman.kuodis on 9/30/2016.
 */

public class LogMessage {

    private String message;
    private int category;

    public LogMessage(String message, int category) {
        this.message = message;
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
