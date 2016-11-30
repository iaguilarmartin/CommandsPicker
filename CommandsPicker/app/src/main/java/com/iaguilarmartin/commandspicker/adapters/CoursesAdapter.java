package com.iaguilarmartin.commandspicker.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.model.Course;
import com.iaguilarmartin.commandspicker.model.CourseCategory;
import com.iaguilarmartin.commandspicker.model.Utils;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by iaguilarmartin on 30/11/16.
 */

public class CoursesAdapter extends ArrayAdapter<CourseCategory> {
    private int mLayoutResource;
    private Context mContext;

    public CoursesAdapter(Context context, int resource, List<CourseCategory> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

        mContext = context;
        mLayoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CourseCategory courseCategory = getItem(position);
        if (courseCategory instanceof Course) {
            View view = inflater.inflate(mLayoutResource, null);
            Course course = (Course) courseCategory;

            TextView tvName = (TextView) view.findViewById(R.id.courseName);
            tvName.setText(course.getName());

            TextView tvPrice = (TextView) view.findViewById(R.id.coursePrice);
            tvPrice.setText(course.getFormattedPrice());

            ImageView image = (ImageView) view.findViewById(R.id.courseImage);
            Bitmap bitmap = Utils.getImageFromApplicationData(mContext, course.getImage());
            image.setImageBitmap(bitmap);

            return view;
        } else {
            View view = inflater.inflate(R.layout.list_item_section, null);

            TextView tvTitle = (TextView) view.findViewById(R.id.sectionTitle);
            tvTitle.setText(courseCategory.getName());

            return view;
        }
    }
}
