package com.avantrio.mobileassessment.Models.api;

public interface API
{
    void login(String email, String password, APIListener listener);
}
