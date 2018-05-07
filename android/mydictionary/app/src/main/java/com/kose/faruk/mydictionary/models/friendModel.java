package com.kose.faruk.mydictionary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 3.12.2017.
 */

public class friendModel {
    @SerializedName("id")
    public int id;
    @SerializedName("MainUser")
    public int userName;
    @SerializedName("FriendUser")
    public int password;
}
