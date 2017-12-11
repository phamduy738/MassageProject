
package com.uni.phamduy.massagefinder.module.district;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class District {

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("image")
    @Expose
    private Image image;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
