package com.avantrio.mobileassessment.models.api;

public interface API
{
    void login(String email, String password, APIListener listener);
}
