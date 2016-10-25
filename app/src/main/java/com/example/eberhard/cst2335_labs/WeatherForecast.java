package com.example.eberhard.cst2335_labs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView currentTemp;
    private TextView maxTemp;
    private TextView minTemp;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        if(isOnline()){
            ForecastQuery task = new ForecastQuery();
            task.execute();
        }else{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(WeatherForecast.this.getApplicationContext(),"Shit, didn't connect", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private class ForecastQuery extends AsyncTask<String,Integer,String>{

        String min;
        String max;
        String current;
        String icon;
        Bitmap weatherPic;
        final static String CITY = "ottawa,ca";
        final static String API_KEY = "d99666875e0e51521f0040a3d97d0f6a";
        final static String MODE = "xml";
        final static String UNITS = "metric";
        final static String ICON_URL = "http://openweathermap.org/img/w/";

        @Override
        protected String doInBackground(String... params) {
            Log.i(ACTIVITY_NAME,"Executing doInBackground()");
            HttpURLConnection connection = null;
            try{
                URL dataURL = new URL("http://api.openweathermap.org/data/2.5/weather?q="
                        + CITY
                        +"&APPID=" + API_KEY
                        +"&mode=" + MODE
                        +"&units=" + UNITS);

                connection = (HttpURLConnection) dataURL.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int status = connection.getResponseCode();
                if(status == 200){
                    InputStream input = connection.getInputStream();
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(input, null);
                    int count = 0;

                    while(parser.next() != XmlPullParser.END_DOCUMENT){

                        if(parser.getEventType() != XmlPullParser.END_TAG){

                            if(parser.getName() != null){

                                if(parser.getName().equals("temperature")){
                                    current = parser.getAttributeValue("", "value");
                                    Log.i(ACTIVITY_NAME,"Updated current temperature");
                                    publishProgress(25);
                                    min = parser.getAttributeValue("", "min");
                                    Log.i(ACTIVITY_NAME,"Updated min temperature");
                                    publishProgress(50);
                                    max = parser.getAttributeValue("", "max");
                                    Log.i(ACTIVITY_NAME,"Updated max temperature");
                                    publishProgress(75);
                                }

                                if(parser.getName().equals("weather")){
                                    icon = parser.getAttributeValue("", "icon");
                                }
                            }
                        }
                    }
                }
                Log.i(ACTIVITY_NAME,"Looking for file " + icon + ".png");
                if(!doesFileExist(icon + ".png")){
                    Log.i(ACTIVITY_NAME,"File: " + icon + ".png Not Found...Downloading...");
                    connection.disconnect();
                    dataURL = new URL(ICON_URL + icon + ".png");
                    connection = (HttpURLConnection) dataURL.openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    status = connection.getResponseCode();

                    if(status == 200){

                        weatherPic = BitmapFactory.decodeStream(connection.getInputStream());
                        FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                        weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        publishProgress(100);
                        outputStream.flush();
                        outputStream.close();
                    }
                }else{
                    Log.i(ACTIVITY_NAME,"File: " + icon + ".png Found...");

                    File file = getBaseContext().getFileStreamPath(icon + ".png");
                    FileInputStream input = new FileInputStream(file);
                    weatherPic = BitmapFactory.decodeStream(input);
                }

            }catch(IOException | XmlPullParserException | NullPointerException e){
                e.printStackTrace();

            }finally{
                connection.disconnect();
                publishProgress(100);
            }
            return "";
        }

        protected void onPostExecute(String result){
            Log.i(ACTIVITY_NAME,"Executing onPostExecute()");
            currentTemp = (TextView)findViewById(R.id.currentTemp);
            maxTemp = (TextView)findViewById(R.id.maxTemp);
            minTemp = (TextView)findViewById(R.id.minTemp);
            image = (ImageView) findViewById(R.id.imageView);

            currentTemp.setText(current + "°C");
            maxTemp.setText(max + "°C");
            minTemp.setText(min + "°C");
            image.setImageBitmap(weatherPic);

            //progressBar.setVisibility(View.GONE);
        }

        protected void onProgressUpdate(Integer...params){
            Log.i(ACTIVITY_NAME,"Executing onProgress()");
            progressBar.setProgress(params[0]);
        }
    }

    public boolean doesFileExist(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

    protected static final String ACTIVITY_NAME ="WeatherForecast";
}
