package com.example.eberhard.cst2335_labs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListItemsActivity.class);
                startActivityForResult(intent, 5);
            }
        });
        chat = (Button)findViewById(R.id.startChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(getApplicationContext(),ChatWindow.class);
                startActivity(intent);
            }
        });

        weather = (Button)findViewById(R.id.startWeather);
        weather.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
                Intent intent = new Intent(getApplicationContext(), WeatherForecast.class);
                startActivity(intent);
            }
        });
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 5){
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
            if(resultCode == Activity.RESULT_OK){
                Context context = getApplicationContext();
                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(context, "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_LONG).show();
            }
        }
    }

    protected static final String ACTIVITY_NAME ="StartActivity";
    private Button button;
    private Button chat;
    private Button weather;

}
