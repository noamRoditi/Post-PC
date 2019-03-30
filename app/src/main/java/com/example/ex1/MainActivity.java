package com.example.ex1;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton sendButton;
//    private TextView textView;
    private EditText editText;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Message> messageList = new ArrayList<>();
    private static final String EDIT_TEXT = "TextInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Message message = new Message("one");
        messageList.add(message);
        message = new Message("two");
        messageList.add(message);
        message = new Message("three");
        messageList.add(message);
        message = new Message("four");
        messageList.add(message);
        message = new Message("five");
        messageList.add(message);
        message = new Message("six");
        messageList.add(message);
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

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(EDIT_TEXT, editText.getText());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getCharSequence(EDIT_TEXT));
    }
}
