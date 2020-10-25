package com.wtm.anshime.model;

public class ChatMessage{

    private String userName;
    private String content;
    private String timeStamp;

    public ChatMessage(){}

    public ChatMessage(String userName, String content, String timeStamp) {
        this.userName = userName;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
