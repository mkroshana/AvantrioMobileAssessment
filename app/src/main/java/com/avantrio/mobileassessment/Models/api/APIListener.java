package com.avantrio.mobileassessment.Models.api;

import com.avantrio.mobileassessment.Models.UserToken;

public interface APIListener
{
    void onLogin (UserToken userToken);
}
