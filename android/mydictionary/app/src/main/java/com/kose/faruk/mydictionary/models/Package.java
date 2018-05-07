package com.kose.faruk.mydictionary.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 11.12.2017.
 */

public class Package implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("packageName")
    public String packageName;
    @SerializedName("kullaniciUser")
    public kullanici kullaniciUser;
    @SerializedName("friends")
    public List<kullanici> friends;
    @SerializedName("words")
    public List<word> words;
}
