package com.example.birdsoffeather.model;

import java.util.List;

public interface IPerson {
    public String getId();
    public String getName();
    public String getURL();
    public List<String> getCourses();

    public String getUser();

    public boolean getWaveTo();
    public boolean getWaveFrom();
    public boolean getFavStatus();
}
