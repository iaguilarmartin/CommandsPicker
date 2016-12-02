package com.iaguilarmartin.commandspicker.adapters;

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

/**
 * Created by iaguilarmartin on 30/11/16.
 */

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
            View view = inflater.inflate(mLayoutResource, null);
            Course course = (Course) courseItem;

            TextView tvName = (TextView) view.findViewById(R.id.courseName);
            tvName.setText(course.getName());

            if (mLayoutResource == R.layout.list_item_course) {
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
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
                    params.rightMargin = 25;
                    icon.setLayoutParams(params);
                    icon.setImageBitmap(iconBitmap);

                    allergensLayout.addView(icon);
                }
            } else {
                TextView tvSuggestions = (TextView) view.findViewById(R.id.courseSeggestions);
                if (course.getSuggestions().length() > 0){
                    tvSuggestions.setText(course.getSuggestions());
                } else {
                    tvSuggestions.setVisibility(View.GONE);
                }
            }

            return view;
        } else {
            CourseCategory courseCategory = (CourseCategory) courseItem;
            View view = inflater.inflate(R.layout.list_item_section, null);

            TextView tvTitle = (TextView) view.findViewById(R.id.sectionTitle);
            tvTitle.setText(courseCategory.getName());

            return view;
        }
    }

    public interface CourseAdapterItem {
    }
}
