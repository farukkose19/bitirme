package com.kose.faruk.mydictionary.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lenovo on 3.12.2017.
 */

public class kullanici {
    @SerializedName("id")
    public int id;
    @SerializedName("userName")
    public String userName;
    @SerializedName("password")
    public String password;
    @SerializedName("name")
    public String name;
    @SerializedName("surname")
    public String surname;

    @SerializedName("words")
    public List<word> words;
    @SerializedName("packagesUser")
    public List<Integer> paketFriend;
    @SerializedName("friends")
    public List<kullanici> friends;


    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
