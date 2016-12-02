package com.iaguilarmartin.commandspicker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragments.CoursesFragment;
import com.iaguilarmartin.commandspicker.fragments.TableDetailFragment;
import com.iaguilarmartin.commandspicker.model.CommanderApplication;
import com.iaguilarmartin.commandspicker.model.Course;
import com.iaguilarmartin.commandspicker.model.Courses;

import java.io.Serializable;

public class CoursesActivity extends AppCompatActivity implements CoursesFragment.OnCourseSelectedListener {

    public static final String EXTRA_TABLE_NUMBER = "tableNumber";
    public static final String COURSE_EXTRA = "CourseExtra";
    public static final int COURSE_ADD_RESULT = 0;

    int mTableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        mTableNumber = getIntent().getIntExtra(EXTRA_TABLE_NUMBER, -1);

        setTitle(getString(R.string.courses_activity_title));
    }

    @Override
    public void onCourseSelected(Course course) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(COURSE_EXTRA, course);
        startActivityForResult(intent, COURSE_ADD_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COURSE_ADD_RESULT && resultCode == RESULT_OK) {
            Course course = (Course) data.getSerializableExtra(CourseDetailActivity.EXTRA_NEW_COURSE);
            CommanderApplication app = (CommanderApplication) getApplication();
            app.addCourse(mTableNumber, course);
            Toast.makeText(this, R.string.course_added_message, Toast.LENGTH_SHORT).show();
        }
    }
}
