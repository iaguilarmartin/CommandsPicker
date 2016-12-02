package com.iaguilarmartin.commandspicker.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragments.CourseDetailFragment;
import com.iaguilarmartin.commandspicker.fragments.CoursesFragment;
import com.iaguilarmartin.commandspicker.model.Allergen;
import com.iaguilarmartin.commandspicker.model.Course;
import com.iaguilarmartin.commandspicker.model.Utils;

public class CourseDetailActivity extends AppCompatActivity implements CourseDetailFragment.OnAddCourseListener {

    public static final String EXTRA_NEW_COURSE = "newCourse";

    Course mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        setTitle(getString(R.string.course_detail_activity_title));
        mCourse = (Course) getIntent().getSerializableExtra(CoursesActivity.COURSE_EXTRA);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment_course_detail) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_course_detail, CourseDetailFragment.newInstance(mCourse))
                    .commit();
        }
    }

    @Override
    public void onAddCourse(Course course) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_NEW_COURSE, course);

        setResult(RESULT_OK, returnIntent);

        finish();
    }
}
