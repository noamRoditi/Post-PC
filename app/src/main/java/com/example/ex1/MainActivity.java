package com.example.ex1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private FloatingActionButton sendButton;
    private EditText editText;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Message> messageList = new ArrayList<>();
    private static final String EDIT_TEXT = "TextInput";
    private Context context;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sendButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        editText = (EditText)findViewById(R.id.plain_text_input);
        db = FirebaseFirestore.getInstance();
        mAdapter = new MyAdapter(messageList, db);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        context = getApplicationContext();
        mAdapter.notifyDataSetChanged();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();

                if (input.equals("")) {

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
        messageList.clear();
        resoteFromDB();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(EDIT_TEXT, editText.getText());
        for(int i=0;i<messageList.size();i++)
        {
            final Message message = messageList.get(i);
            db.collection("messages").document(message.getId()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(!task.getResult().exists()){
                        db.collection("messages").document(message.getId())
                                .set(message)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Write Failed", "Error writing document", e);
                                    }
                                });
                    }
                }
            });
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getCharSequence(EDIT_TEXT));
    }

    protected void resoteFromDB(){
        db.collection("messages").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> documentData = document.getData();
                        String message = (String)documentData.get("message");
                        String id = (String)documentData.get("id");
                        String timeStamp = (String)documentData.get("timeStamp");
                        Message messageToAdd = new Message(message,timeStamp,id);
                        messageList.add(messageToAdd);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("ERROR", "Error getting documents", task.getException());
                }
            }
        });
    }
}
