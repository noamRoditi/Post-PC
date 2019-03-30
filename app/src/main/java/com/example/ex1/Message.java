package com.example.ex1;

public class Message {
    private String message;

    public Message(String textInput){
        this.message = textInput;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
