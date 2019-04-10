package com.example.ex1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private FloatingActionButton sendButton;
    private EditText editText;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Message> messageList = new ArrayList<>();
    private static final String EDIT_TEXT = "TextInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sendButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        editText = (EditText)findViewById(R.id.plain_text_input);

        mAdapter = new MyAdapter(messageList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if (input.equals("")) {
                    Context context = getApplicationContext();
                    Integer duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "you can't send an empty message, oh silly!", duration);
                    toast.show();
                    return;
                }
                editText.setText("");
                Message message = new Message(input);
                messageList.add(message);
                mAdapter.notifyDataSetChanged();
            }
        });
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(this);
        messageList.clear();
        int size = mSharedPreference1.getInt(CustomApplicationClass.MESSAGECOUNT, 0);
        for(int i=0;i<size;i++)
        {
            Message messageToAdd = new Message(mSharedPreference1.getString("Status_" + i, null)) ;
            messageList.add(messageToAdd);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(EDIT_TEXT, editText.getText());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt(CustomApplicationClass.MESSAGECOUNT,messageList.size()).apply();
        for(int i=0;i<messageList.size();i++)
        {
            prefs.edit().putString("Status_" + i, messageList.get(i).getMessage()).apply();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getCharSequence(EDIT_TEXT));
    }
}
