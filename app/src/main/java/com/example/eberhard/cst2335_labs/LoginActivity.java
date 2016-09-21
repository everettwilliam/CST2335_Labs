package com.example.eberhard.cst2335_labs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

//        loginButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//
//                SharedPreferences.Editor editor = shared.edit();
//                EditText loginAddress = (EditText) findViewById(R.id.editLogin);
//                editor.putString(getString(R.string.login_string),loginAddress.getText().toString());
//                editor.commit();
//
//                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
//        String defaultValue = getResources().getString(R.string.login_string);
//        String email = shared.getString(getString(R.string.login_string), defaultValue);
//        EditText loginAddress = (EditText) findViewById(R.id.editLogin);
//        loginAddress.setText(email);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

      protected static final String ACTIVITY_NAME = "LoginActivity";
      protected Button button = (Button) findViewById(R.id.loginButton);
//    SharedPreferences shared = this.getPreferences(Context.MODE_PRIVATE);
}
