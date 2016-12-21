package com.iaguilarmartin.commandspicker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.model.Allergen;
import com.iaguilarmartin.commandspicker.model.Course;
import com.iaguilarmartin.commandspicker.model.CourseCategory;
import com.iaguilarmartin.commandspicker.model.Utils;

import java.util.List;

// This adapter is used to display courses information in different formats
public class CoursesAdapter extends ArrayAdapter<CoursesAdapter.CourseAdapterItem> {
    private int mLayoutResource;
    private Context mContext;

    public CoursesAdapter(Context context, int resource, List<CourseAdapterItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

        mContext = context;
        mLayoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CourseAdapterItem courseItem = getItem(position);
        if (courseItem instanceof Course) {

            // If item to display is a course information is displaying depending on
            // the layout resource especified during initialization
            View view = inflater.inflate(mLayoutResource, null);
            Course course = (Course) courseItem;

            TextView tvName = (TextView) view.findViewById(R.id.courseName);
            tvName.setText(course.getName());

            if (mLayoutResource == R.layout.list_item_course) {
                // This peace of code display complete information about the course
                // it is used in CoursesFragment
                TextView tvPrice = (TextView) view.findViewById(R.id.coursePrice);
                tvPrice.setText(Utils.formatDoubleToPrice(course.getPrice()));

                ImageView image = (ImageView) view.findViewById(R.id.courseImage);
                Bitmap bitmap = Utils.getImageFromApplicationData(mContext, course.getImage());
                image.setImageBitmap(bitmap);

                LinearLayout allergensLayout = (LinearLayout) view.findViewById(R.id.allergensLinearLayout);
                for (Allergen allergen:
                        course.getAllergens()) {

                    Bitmap iconBitmap = Utils.getImageFromApplicationData(mContext, allergen.getIcon());

                    ImageView icon = new ImageView(mContext);

                    int allergenSize = (int) mContext.getResources().getDimension(R.dimen.allergens_list_item_size);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allergenSize, allergenSize);
                    params.rightMargin = (int) mContext.getResources().getDimension(R.dimen.allergens_list_separation);
                    icon.setLayoutParams(params);
                    icon.setImageBitmap(iconBitmap);

                    allergensLayout.addView(icon);
                }
            } else {
                // Just displaying course name and user suggestions.
                // It is used in TableDetailFragment
                TextView tvSuggestions = (TextView) view.findViewById(R.id.courseSeggestions);
                if (course.getSuggestions().length() > 0){
                    tvSuggestions.setText(course.getSuggestions());
                } else {
                    tvSuggestions.setVisibility(View.GONE);
                }
            }

            return view;
        } else {

            // Displaying course category layout
            CourseCategory courseCategory = (CourseCategory) courseItem;
            View view = inflater.inflate(R.layout.list_item_section, null);

            TextView tvTitle = (TextView) view.findViewById(R.id.sectionTitle);
            tvTitle.setText(courseCategory.getName());

            return view;
        }
    }

    // Interface created to reuse adapter with courses and courses categories
    public interface CourseAdapterItem {
    }
}
