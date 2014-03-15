package com.yeyaxi.android.fishinghelper.dataUtil;



/**
 * Created by yaxi on 14/03/2014.
 */
public class Fish {

    private int id;
    private int timeStamp;
    private float latitude;
    private float longitude;
    private String angler;
    private float fishLength;
    private float fishWeight;
    private String bait;
    private String imgPath;
    private String note;

    /**
     * Empty Contructor
     */
    public Fish() {

    }

    public Fish(int id, int timeStamp, float latitude, float longitude, String angler, float fishLength,
                float fishWeight, String bait, String imgPath, String note) {
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.angler = angler;
        this.fishLength = fishLength;
        this.fishWeight = fishWeight;
        this.bait = bait;
        this.imgPath = imgPath;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAngler() {
        return angler;
    }

    public void setAngler(String angler) {
        this.angler = angler;
    }

    public float getFishLength() {
        return fishLength;
    }

    public void setFishLength(float fishLength) {
        this.fishLength = fishLength;
    }

    public float getFishWeight() {
        return fishWeight;
    }

    public void setFishWeight(float fishWeight) {
        this.fishWeight = fishWeight;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
