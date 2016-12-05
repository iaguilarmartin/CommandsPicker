package com.iaguilarmartin.commandspicker.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragments.CourseDetailFragment;
import com.iaguilarmartin.commandspicker.model.Course;

public class CourseDetailActivity extends AppCompatActivity implements CourseDetailFragment.OnAddCourseListener {

    public static final String EXTRA_NEW_COURSE = "newCourse";

    Course mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        setTitle(getString(R.string.course_detail_activity_title));

        // Get course received inside intent extras
        mCourse = (Course) getIntent().getSerializableExtra(CoursesActivity.COURSE_EXTRA);

        // Displaying course information inside a CourseDetailFragment
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment_course_detail) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_course_detail, CourseDetailFragment.newInstance(mCourse))
                    .commit();
        }
    }

    @Override
    // Event fired when user clicks on "Add Course" option menu item
    public void onAddCourse(Course course) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_NEW_COURSE, course);

        setResult(RESULT_OK, returnIntent);

        finish();
    }
}
