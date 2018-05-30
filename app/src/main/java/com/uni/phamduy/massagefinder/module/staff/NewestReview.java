
package com.uni.phamduy.massagefinder.module.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewestReview {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reviewer")
    @Expose
    private String reviewer;
    @SerializedName("reviewDate")
    @Expose
    private String reviewDate;
    @SerializedName("reviewTitle")
    @Expose
    private String reviewTitle;
    @SerializedName("reviewContent")
    @Expose
    private String reviewContent;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("staffId")
    @Expose
    private String staffId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

}
