package com.example.birdsoffeather.model;

import java.util.List;

public interface IPerson {
    public int getId();
    public String getName();
    public String getURL();
    public List<String> getCourses();
    public boolean getWaveTo();
    public boolean getWaveFrom();
    public boolean getFavStatus();
}
