package com.jookershop.crazysticker.glucky;

import org.json.JSONException;
import org.json.JSONObject;

public class FnHistory implements java.io.Serializable {
//	{"ts":1433727974156,"h":"29FF1053B1E30818A1131500269B50549B5C7285##180#ss"}
	private long ts;
	private String uid;
	private String baseNumber;
	private String msg;
	
	public static FnHistory parse(JSONObject ob) {
		FnHistory bh = new FnHistory();
		try {
			if(ob.has("ts") && !ob.isNull("ts"))
				bh.setTs(ob.getLong("ts"));
			if(ob.has("h") && !ob.isNull("h")) {
				String [] raw = ob.getString("h").split("##");
				bh.setUid(raw[0]);
				if(raw.length>1)
					bh.setBaseNumber(raw[1]);
				if(raw.length>2)
					bh.setMsg(raw[2]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bh;
	}

	public boolean isSame(int a) {
		try {
			if (baseNumber != null) {
				if(Integer.parseInt(baseNumber) == a)
					return true;
				else return false;
			}
		} catch (Throwable e) {
			return false;
		}
		return false;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
	
	
	
}
