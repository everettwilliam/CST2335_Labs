package com.example.eberhard.cst2335_labs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        listView = (ListView)findViewById(R.id.chatListView);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        textEdit = (EditText)findViewById(R.id.chatText);
        chat = (Button)findViewById(R.id.sendButton);
        chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                messages.add(textEdit.getText().toString());
                messageAdapter.notifyDataSetChanged();
                textEdit.setText("");
            }
        });
    }

    ListView listView;
    Button chat;
    EditText textEdit;
    ArrayList<String> messages = new ArrayList<>();

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context context){
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
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(getItem(position));
            return result;

        }
    }
}
