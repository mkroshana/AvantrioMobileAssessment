package com.avantrio.mobileassessment.Models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class UserToken
{
    public static UserToken getUserToken (JSONObject jsonObject) throws JSONException
    {
        String token = jsonObject.getString("token");
        return new UserToken(token);
    }

    private final String token;

    public UserToken (String token)
    {
        this.token = token;
    }

    @Override
    public boolean equals (Object obj)
    {
        boolean result = false;

        if (obj instanceof UserToken)
        {
            UserToken that = (UserToken) obj;
            if (this.token.equals(that.token))
            {
                result = true;
            }
        }
        return result;
    }

    @NonNull
    @Override
    public String toString()
    {
        return  this.token;
    }
}
