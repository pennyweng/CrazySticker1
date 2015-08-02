package com.jookershop.crazysticker;

import org.json.JSONException;
import org.json.JSONObject;

public class BidHistory implements java.io.Serializable {
	private long ts;
	private String uid;
	private int baseNumber;
	private String nickName;
	
	public static BidHistory parse(JSONObject ob) {
		BidHistory bh = new BidHistory();
		try {
			if(ob.has("ts") && !ob.isNull("ts"))
				bh.setTs(ob.getLong("ts"));
			if(ob.has("h") && !ob.isNull("h")) {
				String [] raw = ob.getString("h").split("##");
				bh.setUid(raw[0]);
				bh.setBaseNumber(Integer.parseInt(raw[1]));
				bh.setNickName(raw[2]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bh;
	}
	
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getBaseNumber() {
		return baseNumber;
	}
	public void setBaseNumber(int baseNumber) {
		this.baseNumber = baseNumber;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
}
