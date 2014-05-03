package com.lehuo.ui.custom.list;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public abstract class MspJsonItem extends JsonImport implements ListItemImpl {

	public MspJsonItem() {
		super();
	}

	public MspJsonItem(JSONObject job) {
		super(job);
	}


}
