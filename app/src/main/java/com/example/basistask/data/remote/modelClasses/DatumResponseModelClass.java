package com.example.basistask.data.remote.modelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// this class represents each element in the list. Id is the given id and text is the content
public class DatumResponseModelClass {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text")
    @Expose
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
