package com.example.mydoctor.Model;

public class Messages {
    private String key;
    private String date;
    private boolean isseen;
    private String message;
    private String receiver;
    private String sender;



    public Messages(String key,String date,boolean isseen, String message, String receiver,String sender) {
        this.key = key;
        this.date = date;
        this.isseen = isseen;
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;


    }

    public Messages(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean issen) {
        this.isseen = issen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
