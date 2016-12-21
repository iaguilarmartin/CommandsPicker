package com.iaguilarmartin.commandspicker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.model.Allergen;
import com.iaguilarmartin.commandspicker.model.Course;
import com.iaguilarmartin.commandspicker.model.Utils;

public class CourseDetailFragment extends Fragment {

    private final static String ARG_COURSE = "argCourse";

    private Course mCourse;
    private OnAddCourseListener mOnAddCourseListener;

    public static CourseDetailFragment newInstance(Course course) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_COURSE, course);

        CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
        courseDetailFragment.setArguments(arguments);

        return courseDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_COURSE);
        }

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_course_detail, container, false);

        TextView tvName = (TextView) root.findViewById(R.id.courseName);
        tvName.setText(mCourse.getName());

        TextView tvPrice = (TextView) root.findViewById(R.id.coursePrice);
        tvPrice.setText(Utils.formatDoubleToPrice(mCourse.getPrice()));

        ImageView image = (ImageView) root.findViewById(R.id.courseImage);
        Bitmap bitmap = Utils.getImageFromApplicationData(getActivity(), mCourse.getImage());
        image.setImageBitmap(bitmap);

        LinearLayout allergensLayout = (LinearLayout) root.findViewById(R.id.allergensLinearLayout);
        for (Allergen allergen:
                mCourse.getAllergens()) {

            Bitmap iconBitmap = Utils.getImageFromApplicationData(getActivity(), allergen.getIcon());

            ImageView icon = new ImageView(getActivity());
            int allergenSize = (int) getResources().getDimension(R.dimen.allergens_list_item_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allergenSize, allergenSize);
            params.rightMargin = (int) getResources().getDimension(R.dimen.allergens_list_separation);
            icon.setLayoutParams(params);
            icon.setImageBitmap(iconBitmap);

            allergensLayout.addView(icon);
        }

        TextView tvDescription = (TextView) root.findViewById(R.id.courseDescription);
        tvDescription.setText(mCourse.getDescription());

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.course_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_course_menu_option) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle(R.string.add_course_menu_text);
            final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_course, null);
            dialogBuilder.setMessage(R.string.course_suggestions);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText etSuggestions = (EditText) dialogView.findViewById(R.id.suggestions_edit_text);
                    if (mOnAddCourseListener != null) {
                        mCourse.setSuggestions(etSuggestions.getText().toString());
                        mOnAddCourseListener.onAddCourse(mCourse);
                    }
                }
            });
            dialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }

        return superValue;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof  OnAddCourseListener) {
            mOnAddCourseListener = (OnAddCourseListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof  OnAddCourseListener) {
            mOnAddCourseListener = (OnAddCourseListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnAddCourseListener = null;
    }

    public interface OnAddCourseListener {
        void onAddCourse(Course course);
    }
}
