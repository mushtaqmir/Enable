package com.example.mushtaqmir.app4;

import android.content.Context;
import android.net.http.RequestQueue;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.HttpGet;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by Mushtaq.Mir on 6/29/2018.
 */

public class AzureConnector {

    public void getRequest(Context ctx,String message) {

        String url="http://shellsandbox.azurewebsites.net/twitter?text="+message;
       com.android.volley.RequestQueue requestQueue= Volley.newRequestQueue(ctx);
        JsonObjectRequest objectRequest=new JsonObjectRequest(
                com.android.volley.Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                Log.d("RESTRESPONSE",response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESTERROR",error.toString());


                    }
                }
        );
        Log.d("OBJECT",objectRequest.toString());
        requestQueue.add(objectRequest);

   }
}