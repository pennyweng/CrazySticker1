package com.jookershop.crazysticker.point;

/**
 * Created by Penny_Weng on 2015/7/3.
 */
public class HistoryItem {


    private long playTime;
    private String from;
    private int point;
    private String product;


    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
