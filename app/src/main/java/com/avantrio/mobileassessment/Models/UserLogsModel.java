package com.avantrio.mobileassessment.Models;

public class UserLogsModel
{
    private String date;
    private String time;
    private String alertView;
    private double locationLat;
    private double locationLong;

    public UserLogsModel()
    {

    }
    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getAlertView()
    {
        return alertView;
    }

    public void setAlertView(String alertView)
    {
        this.alertView = alertView;
    }

    public double getLocationLat()
    {
        return locationLat;
    }

    public void setLocationLat(double locationLat)
    {
        this.locationLat = locationLat;
    }

    public double getLocationLong()
    {
        return locationLong;
    }

    public void setLocationLong(double locationLong)
    {
        this.locationLong = locationLong;
    }

}
