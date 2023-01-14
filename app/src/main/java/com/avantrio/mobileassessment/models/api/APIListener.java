package com.avantrio.mobileassessment.models.api;

import com.avantrio.mobileassessment.models.UserToken;

public interface APIListener
{
    void onLogin (UserToken userToken);
}
