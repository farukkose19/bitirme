package com.kose.faruk.mydictionary.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammed Faruk KOSE on 1.05.2018.
 */

public class game {
    @SerializedName("id")
    public int id;
    @SerializedName("soru")
    public String soru;
    @SerializedName("cevap1")
    public String cevap1;
    @SerializedName("cevap2")
    public String cevap2;
    @SerializedName("cevap3")
    public String cevap3;
    @SerializedName("cevap4")
    public String cevap4;
    @SerializedName("dogruCevap")
    public int dogruCevap;
    @SerializedName("owner")
    public kullanici owner;
    @SerializedName("gameFriends")
    public List<kullanici> gameFriends;
}
