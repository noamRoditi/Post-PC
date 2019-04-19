package com.example.ex1;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Message implements Parcelable {
    @NonNull
    private String message;
    private String timeStamp;
    private String id;
    @PrimaryKey
    private int Location;
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
        return message + "-timeStamp-" + timeStamp + "-uniqueID-" + id;
    }
    public Message(String textInput){
        this.message = textInput;
        Long tsLong = System.currentTimeMillis()/1000;
        timeStamp = tsLong.toString();
        id = UUID.randomUUID().toString();
    }public Message(String textInput, String timeStamp, String id){
        this.message = textInput;
        this.timeStamp = timeStamp;
        this.id = id;
    }
    private Message(Parcel in) {
        message = in.readString();
    }
    public String getMessage() {
        return message;
    }
    public String getTimeStamp(){
        return timeStamp;
    }
    public String getId(){
        return id;
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
