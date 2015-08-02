package com.jookershop.crazysticker.big;

import org.json.JSONException;
import org.json.JSONObject;

public class BigHistory implements java.io.Serializable {
	private long ts;
	private String uid;
	private String baseNumber;
	private String nickName;
	
	public static BigHistory parse(JSONObject ob) {
		BigHistory bh = new BigHistory();
		try {
			if(ob.has("ts") && !ob.isNull("ts"))
				bh.setTs(ob.getLong("ts"));
			if(ob.has("h") && !ob.isNull("h")) {
				String [] raw = ob.getString("h").split("##");
				bh.setUid(raw[0]);
				bh.setBaseNumber(raw[1]);
				if(raw.length >= 3)
				bh.setNickName(raw[2]);
				else bh.setNickName("");
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
	public String getBaseNumber() {
		return baseNumber;
	}
	public void setBaseNumber(String baseNumber) {
		this.baseNumber = baseNumber;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
}
