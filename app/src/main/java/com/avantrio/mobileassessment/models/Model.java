package com.avantrio.mobileassessment.models;

import android.app.Application;

import com.avantrio.mobileassessment.models.api.API;
import com.avantrio.mobileassessment.models.api.APIListener;
import com.avantrio.mobileassessment.models.api.WebAPI;

public class Model
{
    private static Model sInstance = null;
    private final API mApi;
    private UserToken mUserToken;

    public static Model getInstance(Application application)
    {
        if (sInstance == null)
        {
            sInstance = new Model(application);
        }
        return sInstance;
    }

    private final Application mApplication;

    private Model (Application application)
    {
        mApplication = application;
        mApi = new WebAPI(mApplication);
    }

    public Application getApplication()
    {
        return mApplication;
    }

    public void login (String email, String password, APIListener listener)
    {
        mApi.login(email, password, listener);
    }

    public UserToken getUserToken()
    {
        return mUserToken;
    }

    public void setUserToken(UserToken userToken)
    {
        this.mUserToken = userToken;
    }
}
