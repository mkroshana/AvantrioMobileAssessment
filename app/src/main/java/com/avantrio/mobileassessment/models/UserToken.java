package com.avantrio.mobileassessment.models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserToken
{
    public static UserToken getUserToken (JSONObject jsonObject) throws JSONException
    {
        String token = jsonObject.getString("token");
        UserToken userToken = new UserToken(token);

        return userToken;
    }

    private String token;

    public UserToken (String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Override
    public boolean equals (Object obj)
    {
        boolean result = false;

        if (obj != null && obj instanceof UserToken)
        {
            UserToken that = (UserToken) obj;
            if (this.token.equals(that.token))
            {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        return  this.token;
    }
}
