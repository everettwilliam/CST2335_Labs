package com.example.eberhard.cst2335_labs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        listView = (ListView) findViewById(R.id.chatListView);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        textEdit = (EditText) findViewById(R.id.chatText);
        chat = (Button) findViewById(R.id.sendButton);

        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);//database helper object
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.FIELDS, null, null, null, null, null);
        String[] columnNames = cursor.getColumnNames();

        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                messages.add(message);

                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));


            } while (cursor.moveToNext());
        }

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        for (String element : columnNames) {
            System.out.println(element);
        }

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newMessage = textEdit.getText().toString();
                messages.add(newMessage);
                messageAdapter.notifyDataSetChanged();
                textEdit.setText("");

                ContentValues newValues = new ContentValues();
                newValues.put(ChatDatabaseHelper.KEY_MESSAGE, newMessage);
                database.insertWithOnConflict(ChatDatabaseHelper.TABLE_NAME, null, newValues, SQLiteDatabase.CONFLICT_IGNORE);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        database.close();
    }

    ListView listView;
    Button chat;
    EditText textEdit;
    ArrayList<String> messages = new ArrayList<>();

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public String getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.messageText);
            message.setText(getItem(position));
            return result;
        }
    }

    protected static final String ACTIVITY_NAME = "Chat Window";
    protected static SQLiteDatabase database;

}
