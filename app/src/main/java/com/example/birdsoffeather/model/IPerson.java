package com.example.birdsoffeather.model;

import java.util.List;

public interface IPerson {
    public abstract int getId();
    public String getName();
    public String getURL();
    public List<String> getCourses();
}
