package com.wikimedia.assigment.volley;

import org.json.JSONObject;

public interface IVolleyJsonRespondsListener {

    public void onSuccessJson(JSONObject result, String type);
    public void onFailureJson(int responseCode, String responseMessage);

}
