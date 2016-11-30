package com.iaguilarmartin.commandspicker.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iaguilarmartin.commandspicker.R;
import com.iaguilarmartin.commandspicker.adapters.CoursesAdapter;
import com.iaguilarmartin.commandspicker.model.Courses;
import com.iaguilarmartin.commandspicker.model.Utils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by iaguilarmartin on 30/11/16.
 */

public class CoursesFragment extends Fragment {

    private ListView mListView;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        mListView = (ListView) root.findViewById(R.id.coursesList);

        String coursesJSON = Utils.getAppDataFileContent(getActivity());
        if (coursesJSON == null) {
            mProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.progress_dialog_title),
                    getString(R.string.progress_dialog_text), true);

            String strUrl = getString(R.string.config_remote_resources_url);
            new DownloadApplicationDataAsyncTask().execute(strUrl);
        } else {
            Courses courses = coursesFromJSON(coursesJSON);
            loadTableData(courses);
        }

        return root;
    }

    private void loadTableData(Courses courses) {
        if (courses == null) {
            courses = new Courses();
        }

        CoursesAdapter adapter = new CoursesAdapter(getActivity(), R.layout.list_item_course, courses.getArray(getActivity()));
        mListView.setAdapter(adapter);
    }

    private Courses coursesFromJSON(String json) {
        try {
            return  new Courses(json);
        } catch (JSONException exc) {
            Log.e(getString(R.string.app_name), "Error while parsing courses data: " + exc.getMessage());
            return null;
        }
    }

    class DownloadApplicationDataAsyncTask extends AsyncTask<String, Void, Courses> {

        @Override
        protected Courses doInBackground(String... args) {
            try {
                String baseURL = args[0];
                String fileName = getString(R.string.config_courses_json_file);

                InputStream inputStream = Utils.downloadFileFromURL(baseURL + fileName);
                if (inputStream != null) {
                    String coursesJSON = Utils.convertStreamToString(inputStream);
                    inputStream.close();

                    Courses courses = coursesFromJSON(coursesJSON);

                    for (String icon: courses.getCoursesAndAllergensImages()) {
                        InputStream imageIS = Utils.downloadFileFromURL(baseURL + icon);
                        if (!Utils.saveInputStreamToFile(getActivity(), imageIS, icon)) {
                            return null;
                        }
                    }

                    Utils.setAppDataFileContent(getActivity(), coursesJSON);

                    return courses;
                }

            } catch (IOException exc) {
                Log.e(getString(R.string.app_name), "Error while getting courses data: " + exc.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Courses courses) {
            super.onPostExecute(courses);

            mProgressDialog.hide();

            loadTableData(courses);
        }
    }
}
