package com.kose.faruk.mydictionary.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 18.10.2017.
 */


public class word implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("means")
    public List<mean> means;
    @SerializedName("kullanici")
    public int kullanici;

}
