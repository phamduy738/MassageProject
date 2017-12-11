
package com.uni.phamduy.massagefinder.module.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("period")
    @Expose
    private Integer period;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("priceWithCode")
    @Expose
    private Integer priceWithCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tip")
    @Expose
    private Integer tip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceWithCode() {
        return priceWithCode;
    }

    public void setPriceWithCode(Integer priceWithCode) {
        this.priceWithCode = priceWithCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

}
