
package com.uni.phamduy.massagefinder.module.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverImage {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("link")
    @Expose
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}