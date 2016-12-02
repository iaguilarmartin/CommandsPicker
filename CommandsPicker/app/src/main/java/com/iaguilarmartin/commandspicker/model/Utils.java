package com.iaguilarmartin.commandspicker.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.iaguilarmartin.commandspicker.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by iaguilarmartin on 29/11/16.
 */

public class Utils {

    public static String formatDoubleToPrice(double value) {
        return String.format("%.2f €", value);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static InputStream downloadFileFromURL(String strURL) throws IOException {
        URL url = new URL(strURL);
        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            return httpURLConnection.getInputStream();
        }

        return null;
    }

    public static boolean saveInputStreamToFile(Context context, InputStream inputStream, String fileName) {
        try {
            File file = new File(getApplicationDataDirectory(context), fileName);
            file.getParentFile().mkdirs();

            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            inputStream.close();

            return true;
        } catch (Exception exc) {
            Log.e(context.getString(R.string.app_name), "Error while saving inputStream to file: " + exc.getMessage());

            return false;
        }
    }

    public static Bitmap getImageFromApplicationData(Context context, String imageName) {
        try {
            File file = new File(getApplicationDataDirectory(context), imageName);
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (IOException exc) {
            Log.e(context.getString(R.string.app_name), "Error while trying to get course image: " + exc.getMessage());
            return null;
        }
    }

    public static File getApplicationDataDirectory(Context context) {
        File appDataDIr = context.getDir("AppData", Context.MODE_PRIVATE);
        if (!appDataDIr.exists()) {
            appDataDIr.mkdir();
        }
        return appDataDIr;
    }

    public static String getAppDataFileName(Context context) {
        return context.getString(R.string.config_courses_json_file);
    }

    public static String getAppDataFileContent(Context context) {
        String json = null;

        File jsonFile = new File(getApplicationDataDirectory(context), getAppDataFileName(context));
        if (jsonFile.exists()) {
            try {
                FileInputStream fin = new FileInputStream(jsonFile);
                json = convertStreamToString(fin);
                fin.close();
            } catch (Exception exc) {
                Log.e(context.getString(R.string.app_name), "Error while reading app data file: " + exc.getMessage());
            }
        }

        return json;
    }

    public static void setAppDataFileContent(Context context, String data) {
        try {
            File file = new File(getApplicationDataDirectory(context), getAppDataFileName(context));
            OutputStream out = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException exc) {
            Log.e(context.getString(R.string.app_name), "Error while saving app data file: " + exc.getMessage());
        }
    }
}
