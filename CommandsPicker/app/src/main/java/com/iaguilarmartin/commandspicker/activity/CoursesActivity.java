package com.iaguilarmartin.commandspicker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.fragment.CourseDetailFragment;
import com.iaguilarmartin.commandspicker.fragment.CoursesFragment;
import com.iaguilarmartin.commandspicker.model.CommanderApplication;
import com.iaguilarmartin.commandspicker.model.Course;

public class CoursesActivity extends AppCompatActivity implements CoursesFragment.OnCourseSelectedListener, CourseDetailFragment.OnAddCourseListener {

    public static final String EXTRA_TABLE_NUMBER = "tableNumber";
    public static final String COURSE_EXTRA = "CourseExtra";
    public static final int COURSE_ADD_RESULT = 0;

    private int mTableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting table number received inside intent extras
        mTableNumber = getIntent().getIntExtra(EXTRA_TABLE_NUMBER, -1);

        setTitle(getString(R.string.courses_activity_title));
    }

    @Override
    public void onCourseSelected(Course course) {
        // Implementing different behaviour depending on device screen pixel ratio

        FrameLayout fragmentLayout = (FrameLayout) findViewById(R.id.course_detail_fragment);
        if (fragmentLayout != null) {
            // If current view contains a layout to display course information then
            // a CourseDetailFragment is loaded inside it
            CourseDetailFragment fragment = CourseDetailFragment.newInstance(course);
            getFragmentManager().beginTransaction().replace(R.id.course_detail_fragment, fragment).commit();
        } else {
            // Else course information is displayed in another activity and wait for a result
            // just in case user add a new course to the table
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra(COURSE_EXTRA, course);
            startActivityForResult(intent, COURSE_ADD_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COURSE_ADD_RESULT && resultCode == RESULT_OK) {

            // Course is added to the table
            Course course = (Course) data.getSerializableExtra(CourseDetailActivity.EXTRA_NEW_COURSE);
            addCourse(course);
        }
    }

    @Override
    public void onAddCourse(Course course) {
        addCourse(course);
    }

    private void addCourse(Course course) {
        CommanderApplication app = (CommanderApplication) getApplication();
        app.addCourse(mTableNumber, course);

        // Giving feedback to user about course adding operation
        Toast.makeText(this, R.string.course_added_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superResult = super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return superResult;
    }
}
