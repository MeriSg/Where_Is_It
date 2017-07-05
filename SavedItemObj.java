package com.meri_sg.where_is_it.DB;

/**
 * Created by lenovo on 29-Nov-16.
 */

public class SavedItemObj {

    private String itemname;
    private String img;
    private String placedescription;
    private double lat;
    private double lng;
    private String imgtime;
    private String alerton;
    private String alerttime;
    private String comments;

    public SavedItemObj(String itemname, String img, String placedescription, double lat, double lng, String imgtime, String alerton, String alerttime, String comments) {
        this.itemname = itemname;
        this.img = img;
        this.placedescription = placedescription;
        this.lat = lat;
        this.lng = lng;
        this.imgtime = imgtime;
        this.alerton = alerton;
        this.alerttime = alerttime;
        this.comments = comments;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPlacedescription() {
        return placedescription;
    }

    public void setPlacedescription(String placedescription) {
        this.placedescription = placedescription;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImgtime() {
        return imgtime;
    }

    public void setImgtime(String imgtime) {
        this.imgtime = imgtime;
    }

    public String getAlerton() {
        return alerton;
    }

    public void setAlerton(String alerton) {
        this.alerton = alerton;
    }

    public String getAlerttime() {
        return alerttime;
    }

    public void setAlerttime(String alerttime) {
        this.alerttime = alerttime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
