package com.example.birdsoffeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;


public class searchingActivity extends AppCompatActivity {
    private final String EXURL = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;

    //TODO: List to store fetch students

    private static final String TAG = "Lab5-Nearby";
    private MessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        setTitle("Searching");

        //initialize local database
        AppDatabase db = AppDatabase.singleton(this);
        List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
        studentList.remove(0);

        personRecyclerView = findViewById(R.id.search_recycler_view);

        personLayoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(personLayoutManager);

        // sorting list of students in order of common classes
        Collections.sort(studentList, new Comparator<PersonWithCourses>() {
            @Override
            public int compare(PersonWithCourses p1, PersonWithCourses p2) {
                return p2.getCourses().size() - p1.getCourses().size();
            }
        });

        peopleViewAdapter = new PeopleViewAdapter(studentList);

    }

    //search the nearby student who has taken same classes with the user
    public void searchonClick(View view) {
        personRecyclerView.setAdapter(peopleViewAdapter);
        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.d(TAG, "found message: " + new String(message.getContent()));
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Lost messages: " + new String(message.getContent()));
            }

        };
        this.messageListener = new FakedMessageListener(realListener, "Hello world");

    }


    //Go Back
    public void backonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    //Go to the Mock
    public void goToMockOnClick(View view) {
        Intent intent = new Intent(this, MockCSVActivity.class);
        startActivity(intent);
    }
}