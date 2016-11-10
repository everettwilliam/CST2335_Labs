package com.example.eberhard.cst2335_labs;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity implements CustomDialog.NoticeDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new CustomDialog();
        message = getString(R.string.toolbar_snack_one);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,R.string.toolbar_snack_fab, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_one:
                Log.d(ACTIVITY_NAME,"Option 1 selected");
                Snackbar.make(findViewById(R.id.toolbarActivity),message,Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d(ACTIVITY_NAME,"Option 2 selected");
                displayDialog().show();
                break;
            case R.id.action_three:
                Log.d(ACTIVITY_NAME,"Option 3 selected");
                dialog.show(getSupportFragmentManager(), "Dialog");
                break;
            case R.id.settings:
                Log.d(ACTIVITY_NAME,"Settings selected");
                break;
            case R.id.about:
                Log.d(ACTIVITY_NAME,"About selected");
                Toast.makeText(this, "Version 1.0 by Everett Holden", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    public AlertDialog displayDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.toolbar_dialog);
        builder.setPositiveButton(R.string.toolbar_dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                //click ok button
                finish();
            }
        });

        builder.setNegativeButton(R.string.toolbar_dialog_cancel,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                //click cancel
            }
        });
        return builder.create();    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        text = (EditText) dialog.getDialog().findViewById(R.id.dialogMessage);
        message = text.getText().toString();
    }

    protected static final String ACTIVITY_NAME ="Toolbar";
    private EditText text;
    private String message;
    private CustomDialog dialog;

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
}
