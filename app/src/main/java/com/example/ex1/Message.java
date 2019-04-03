package com.example.ex1;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
    private String message;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);

    }
    @Override
    public String toString() {
        return message;
    }
    public Message(String textInput){
        this.message = textInput;
    }
    private Message(Parcel in) {
        message = in.readString();
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
