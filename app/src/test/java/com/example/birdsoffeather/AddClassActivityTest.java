package com.example.birdsoffeather;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.CoursesDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class AddClassActivityTest {
    private AppDatabase db;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
//        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
//        db = AppDatabase.singleton(context);
//        db.useTestSingleton(context);
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Rule
    public ActivityScenarioRule rule =
            new ActivityScenarioRule<AddClassActivity>(AddClassActivity.class);



    @Test
    public void testAddClassClicked() {
        ActivityScenario scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {

            Spinner yearSpinner = (Spinner) activity.findViewById(R.id.YearDropDown);
            assertEquals("2018", (String) yearSpinner.getItemAtPosition(0));
            assertEquals("2019", (String) yearSpinner.getItemAtPosition(1));
        });
    }

    }

//    @Test
//    public void testInitialState() {
//        ActivityScenario scenario = rule.getScenario();
//        scenario.onActivity(activity -> {
//            EditText editSubject = (EditText)  activity.findViewById(R.id.editSubject);
//            assertEquals("", editSubject.getText().toString());
//            EditText editCourse = (EditText)  activity.findViewById(R.id.editCourse);
//            assertEquals("", editCourse.getText().toString());
//        });
//    }

