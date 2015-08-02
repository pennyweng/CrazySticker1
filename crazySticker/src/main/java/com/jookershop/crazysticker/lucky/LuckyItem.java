package com.jookershop.crazysticker.lucky;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Penny_Weng on 2015/5/26.
 */
public class LuckyItem  implements Serializable {
//    "id":"dc1f113a-b4f6-49f0-845c-f059c70fc107",
//            "type":"FR_LINE",
//            "lgid":"41dd1fe5-6cd1-4241-bcc1-4848a07648d4",
//            "name":"黑子的籃球 第2彈",
//            "join_count":2,
//            "img":"https://sdl-stickershop.line.naver.jp/products/0/0/1/3469/LINEStorePC/thumbnail_shop.png",
//            "ct":1432608060069,
//            "tt":"59566002",
//            "finish":true,
//            "winner_id":"29FF1053B1E30818A1131500269B50549B5C7285",
//            "winner_ts":1432608192285,
//            "winner_msg":"cool"

    private String id;
    private String type;
    private String lgid;
    private String name;
    private int joinCount;
    private String imgUrl;
    private long createTime;
    private boolean finish;
    private String winnerId;
    private long winnerTs;
    private String winnerMsg;

    public static LuckyItem parse(JSONObject ja) {
        LuckyItem li = new LuckyItem();
        try {
            if(ja.has("id") && !ja.isNull("id"))
                li.setId(ja.getString("id"));
            if(ja.has("type") && !ja.isNull("type"))
                li.setType(ja.getString("type"));
            if(ja.has("lgid") && !ja.isNull("lgid"))
                li.setLgid(ja.getString("lgid"));
            if(ja.has("name") && !ja.isNull("name"))
                li.setName(ja.getString("name"));
            if(ja.has("join_count") && !ja.isNull("join_count"))
                li.setJoinCount(ja.getInt("join_count"));
            if(ja.has("img") && !ja.isNull("img"))
                li.setImgUrl(ja.getString("img"));
            if(ja.has("ct") && !ja.isNull("ct"))
                li.setCreateTime(ja.getLong("ct"));
            if(ja.has("finish") && !ja.isNull("finish"))
                li.setFinish(ja.getBoolean("finish"));
            if(ja.has("winner_id") && !ja.isNull("winner_id"))
                li.setWinnerId(ja.getString("winner_id"));
            if(ja.has("winner_msg") && !ja.isNull("winner_msg"))
                li.setWinnerMsg(ja.getString("winner_msg"));
            if(ja.has("winner_ts") && !ja.isNull("winner_ts"))
                li.setWinnerTs(ja.getLong("winner_ts"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return li;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLgid() {
        return lgid;
    }

    public void setLgid(String lgid) {
        this.lgid = lgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public long getWinnerTs() {
        return winnerTs;
    }

    public void setWinnerTs(long winnerTs) {
        this.winnerTs = winnerTs;
    }

    public String getWinnerMsg() {
        return winnerMsg;
    }

    public void setWinnerMsg(String winnerMsg) {
        this.winnerMsg = winnerMsg;
    }
}
