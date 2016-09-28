package com.example.eberhard.cst2335_labs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                Context context = getApplicationContext();
                if(isChecked){
                    CharSequence text = (CharSequence)getString(R.string.switch_on);
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }else{
                    CharSequence text = (CharSequence)getString(R.string.switch_off);
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            }
        });
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                Context context = getApplicationContext();
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialog_message);
                    builder.setTitle(R.string.dialog_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id){
                            Intent result = new Intent();
                            String response = (String) getString(R.string.response);
                            result.putExtra("Response", response);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id){
                            checkBox.toggle();
                            setResult(Activity.RESULT_CANCELED);

                        }
                    });
                    builder.show();

                }else{

                }
            }
        });

//        if(savedInstanceState != null){
//            Bitmap image = (Bitmap) savedInstanceState.get("saved_image");
//            imageButton = (ImageButton) findViewById(R.id.imageButton);
//            imageButton.setImageBitmap(image);
//        }
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

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1 ){
            if(resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap image = (Bitmap) extras.get("data");
                ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
                imageButton.setImageBitmap(image);
            }
        }
    }

//    protected void onSaveInstanceState(Bundle savedInstanceState){
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putSerializable("saved_image", (Serializable) imageButton.getDrawable());
//    }
    protected static final String ACTIVITY_NAME ="ListItemsActivity";
    private ImageButton imageButton;
    private Switch switch1;
    private CheckBox checkBox;

}
