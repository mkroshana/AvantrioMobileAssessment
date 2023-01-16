package com.avantrio.mobileassessment.Models;

import android.app.Application;

import com.avantrio.mobileassessment.Models.api.API;
import com.avantrio.mobileassessment.Models.api.APIListener;
import com.avantrio.mobileassessment.Models.api.WebAPI;

public class AuthenticationModel
{
    private static AuthenticationModel sInstance = null;
    private final API mApi;
    private UserToken mUserToken;
    private int UserLogsPosition;

    public static AuthenticationModel getInstance(Application application)
    {
        if (sInstance == null)
        {
            sInstance = new AuthenticationModel(application);
        }
        return sInstance;
    }

    private final Application mApplication;

    private AuthenticationModel(Application application)
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

    public int getUserLogsPosition() {
        return UserLogsPosition;
    }

    public void setUserLogsPosition(int userLogsPosition) {
        UserLogsPosition = userLogsPosition;
    }
}
