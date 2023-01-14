package com.avantrio.mobileassessment.models.api;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.avantrio.mobileassessment.models.Model;
import com.avantrio.mobileassessment.models.UserToken;

import org.json.JSONException;
import org.json.JSONObject;

public class WebAPI implements API
{
    public static final String BASE_URL ="http://apps.avantrio.xyz:8010/";
    private final Application mApplication;
    private RequestQueue mRequestQueue;

    public WebAPI (Application application)
    {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
    }

    public void login (String email, String password, final APIListener listener)
    {
        String url = BASE_URL + "api/user/login";
        JSONObject jsonObject = new JSONObject();

        try
        {
            jsonObject.put("username",email);
            jsonObject.put("password",password);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        UserToken userToken = UserToken.getUserToken(response);
                        listener.onLogin(userToken);
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_LONG).show();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(mApplication, "Incorrect Login Credentials!", Toast.LENGTH_LONG).show();
                }
            };
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
            mRequestQueue.add(request);
        }
        catch (JSONException e)
        {
            Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_LONG).show();
        }

    }
}
