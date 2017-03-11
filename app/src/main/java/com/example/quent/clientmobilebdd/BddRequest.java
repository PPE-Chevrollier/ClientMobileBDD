package com.example.quent.clientmobilebdd;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by quent on 10/03/2017.
 */

public class BddRequest extends StringRequest {
    private static String URL = "http://192.168.152.1/api/";

    public BddRequest(Response.Listener<String> listener, String finUrl) {
        super(Method.GET, URL+finUrl, listener, null);
    }
}
