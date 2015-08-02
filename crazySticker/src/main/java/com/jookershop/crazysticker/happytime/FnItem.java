package com.jookershop.crazysticker.happytime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FnItem implements java.io.Serializable {
	private String id;
	private String type;
	private String lgid;
	private String name;
	private long big;
	private long small;
	private String img;
	private long start;
	private long end;
	private String winnerId;
	private long winnerTs;
	private String winnerMsg;

	private FnHistory[] fnHistory;
	private boolean isFinish;

	//	{"data":{
//		"id":"b953383b-39d7-4408-96d4-e773f8d9f7f9",
//				"type":"FR_LINE",
//				"lgid":"e72d5945-c94e-4d98-9d29-59dc9581ded3",
//				"name":"荷包君的日常貼圖（第一發..",
//				"big":9999999999999,
//				"small":180,
//				"img":"https://sdl-stickershop.line.naver.jp/products/0/0/1/1079278/LINEStorePC/thumbnail_shop.png",
//				"start":1433727959538,
//				"winner_id":null,
//				"winner_ts":0,
//				"winner_msg":null
//	},"history":[{"ts":1433727974156,"h":"29FF1053B1E30818A1131500269B50549B5C7285##180"}]}
	public static FnItem parse(JSONObject ob) {
		FnItem bh = new FnItem();
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
			if(obb.has("big") && !obb.isNull("big"))
				bh.setBig(obb.getLong("big"));
			if(obb.has("small") && !obb.isNull("small"))
				bh.setSmall(obb.getLong("small"));
			if(obb.has("img") && !obb.isNull("img"))
				bh.setImg(obb.getString("img"));
			if(obb.has("start") && !obb.isNull("start"))
				bh.setStart(obb.getLong("start"));
			if(obb.has("end") && !obb.isNull("end"))
				bh.setEnd(obb.getLong("end"));
			if(obb.has("winner_id") && !obb.isNull("winner_id")) {
				bh.setWinnerId(obb.getString("winner_id"));
				bh.setFinish(true);
			}
			if(obb.has("winner_ts") && !obb.isNull("winner_ts"))
				bh.setWinnerTs(obb.getLong("winner_ts"));
			if(obb.has("winner_msg") && !obb.isNull("winner_msg"))
				bh.setWinnerMsg(obb.getString("winner_msg"));

			JSONArray joa = ob.getJSONArray("history");
			ArrayList<FnHistory> tmp = new ArrayList<FnHistory>();
			for (int index = 0; index < joa.length(); index ++) {
				tmp.add(FnHistory.parse1(joa.getJSONObject(index)));
			}
			bh.setFnHistory(tmp.toArray(new FnHistory[]{}));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bh;
	}


	public static FnItem parseWinner(JSONObject obb) {
		FnItem bh = new FnItem();
		try {
			if(obb.has("id") && !obb.isNull("id"))
				bh.setId(obb.getString("id"));
			if(obb.has("type") && !obb.isNull("type"))
				bh.setType(obb.getString("type"));
			if(obb.has("lgid") && !obb.isNull("lgid"))
				bh.setLgid(obb.getString("lgid"));
			if(obb.has("name") && !obb.isNull("name"))
				bh.setName(obb.getString("name"));
			if(obb.has("big") && !obb.isNull("big"))
				bh.setBig(obb.getLong("big"));
			if(obb.has("small") && !obb.isNull("small"))
				bh.setSmall(obb.getLong("small"));
			if(obb.has("img") && !obb.isNull("img"))
				bh.setImg(obb.getString("img"));
			if(obb.has("start") && !obb.isNull("start"))
				bh.setStart(obb.getLong("start"));
			if(obb.has("end") && !obb.isNull("end"))
				bh.setEnd(obb.getLong("end"));
			if(obb.has("winner_id") && !obb.isNull("winner_id")) {
				bh.setWinnerId(obb.getString("winner_id"));
				bh.setFinish(true);
			}
			if(obb.has("winner_ts") && !obb.isNull("winner_ts"))
				bh.setWinnerTs(obb.getLong("winner_ts"));
			if(obb.has("winner_msg") && !obb.isNull("winner_msg"))
				bh.setWinnerMsg(obb.getString("winner_msg"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bh;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getBig() {
		return big;
	}

	public void setBig(long big) {
		this.big = big;
	}

	public long getSmall() {
		return small;
	}

	public void setSmall(long small) {
		this.small = small;
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

	public void setIsFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public boolean isFinish() {
		return isFinish;
	}


	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}


	public FnHistory[] getFnHistory() {
		return fnHistory;
	}


	public void setFnHistory(FnHistory[] fnHistory) {
		this.fnHistory = fnHistory;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}

	
	
	
}
