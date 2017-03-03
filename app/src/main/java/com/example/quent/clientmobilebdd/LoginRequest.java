package com.example.quent.clientmobilebdd;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by quent on 13/12/2016.
 */

public class LoginRequest extends StringRequest {
    private static String LOGIN_REQUEST_URL = "http://192.168.152.1/api-connexion/index/";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.GET, LOGIN_REQUEST_URL+username+"/"+password, listener, null);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
