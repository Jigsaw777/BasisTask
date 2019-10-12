package com.example.basistask.data.remote.modelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//this data response model is the model class which contains the list of DatumResponseModelClass
public class DataResponseModel {
    @SerializedName("data")
    @Expose
    private List<DatumResponseModelClass> data = null;

    public List<DatumResponseModelClass> getData() {
        return data;
    }

    public void setData(List<DatumResponseModelClass> data) {
        this.data = data;
    }
}
