
package com.uni.phamduy.massagefinder.module.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Staff {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("avatar")
    @Expose
    private Avatar avatar;
    @SerializedName("nickName")
    @Expose
    private String nickName;
    @SerializedName("homeTown")
    @Expose
    private String homeTown;
    @SerializedName("averageRating")
    @Expose
    private Double averageRating;
    @SerializedName("numberReview")
    @Expose
    private Double numberReview;
    @SerializedName("newestReview")
    @Expose
    private NewestReview newestReview;
    @SerializedName("placeId")
    @Expose
    private String placeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getNumberReview() {
        return numberReview;
    }

    public void setNumberReview(Double numberReview) {
        this.numberReview = numberReview;
    }

    public NewestReview getNewestReview() {
        return newestReview;
    }

    public void setNewestReview(NewestReview newestReview) {
        this.newestReview = newestReview;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}
