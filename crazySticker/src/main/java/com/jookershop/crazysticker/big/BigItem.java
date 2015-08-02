package com.jookershop.crazysticker.big;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BigItem implements java.io.Serializable {
	
	private String id;
	private String type;
	private String lgid;
	private String name;
	private int baseCount;
	private String img;
	private long end;
	private long start;
	private int nowBase;
	private BigHistory[] bigHistory;
	private boolean isFinish;

	public static BigItem parse(JSONObject ob) {
		BigItem bh = new BigItem();
		try {
			JSONObject obb = ob.getJSONObject("data");
			if(obb.has("id") && !obb.isNull("id"))
				bh.setId(obb.getString("id"));
			if(obb.has("type") && !obb.isNull("type"))
				bh.setType(obb.getString("type"));
			if(obb.has("lgid") && !obb.isNull("lgid"))
				bh.setLgid(obb.getString("lgid"));
			if(obb.has("name") && !obb.isNull("name"))
				bh.setName(obb.getString("name"));
			if(obb.has("base_count") && !obb.isNull("base_count"))
				bh.setBaseCount(obb.getInt("base_count"));
			if(obb.has("img") && !obb.isNull("img"))
				bh.setImg(obb.getString("img"));
			if(obb.has("end") && !obb.isNull("end"))
				bh.setEnd(obb.getLong("end"));
			if(obb.has("start") && !obb.isNull("start"))
				bh.setStart(obb.getLong("start"));
			if(ob.has("now_base") && !ob.isNull("now_base"))
				bh.setNowBase(ob.getInt("now_base"));
			if(ob.has("final") && !ob.isNull("final"))
				bh.setFinish(ob.getBoolean("final"));

			JSONArray joa = ob.getJSONArray("history");
			ArrayList<BigHistory> tmp = new ArrayList<BigHistory>();
			for(int index =0; index < joa.length(); index ++) {
				tmp.add(BigHistory.parse(joa.getJSONObject(index)));
			}
			bh.setBigHistory((BigHistory[]) tmp.toArray(new BigHistory[]{}));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bh;
	}


	public boolean isFinish() {
		return isFinish;
	}


	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}


	public BigHistory[] getBigHistory() {
		return bigHistory;
	}


	public void setBigHistory(BigHistory[] bidHistory) {
		this.bigHistory = bidHistory;
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
	public int getBaseCount() {
		return baseCount;
	}
	public void setBaseCount(int baseCount) {
		this.baseCount = baseCount;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public int getNowBase() {
		return nowBase;
	}
	public void setNowBase(int nowBase) {
		this.nowBase = nowBase;
	}
	
	
	
	
}
