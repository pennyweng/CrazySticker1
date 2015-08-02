package com.jookershop.crazysticker.happytime;

import org.json.JSONException;
import org.json.JSONObject;

public class FnHistory implements java.io.Serializable {
//	{"ts":1433727974156,"h":"29FF1053B1E30818A1131500269B50549B5C7285##180"}
	private long ts;
	private String uid;
	private long baseNumber;
	private String msg;
	
	public static FnHistory parse(JSONObject ob) {
		FnHistory bh = new FnHistory();
		try {
			if(ob.has("ts") && !ob.isNull("ts"))
				bh.setTs(ob.getLong("ts"));
			if(ob.has("h") && !ob.isNull("h")) {
				String [] raw = ob.getString("h").split("##");
				bh.setUid(raw[0]);
				bh.setBaseNumber(Long.parseLong(raw[1]));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bh;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static FnHistory parse1(JSONObject ob) {
		FnHistory bh = new FnHistory();
		try {
			if(ob.has("ts") && !ob.isNull("ts"))
				bh.setTs(ob.getLong("ts"));
			if(ob.has("h") && !ob.isNull("h")) {
				String [] raw = ob.getString("h").split("##");
				if(raw != null && raw.length > 0)
				bh.setUid(raw[0]);
				if(raw != null && raw.length > 1)
				bh.setMsg(raw[1]);
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
	public long getBaseNumber() {
		return baseNumber;
	}
	public void setBaseNumber(long baseNumber) {
		this.baseNumber = baseNumber;
	}
	
	
	
}
