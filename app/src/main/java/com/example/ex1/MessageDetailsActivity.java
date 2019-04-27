package com.example.ex1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class MessageDetailsActivity extends AppCompatActivity {

    private Button deleteButton;
    private TextView textView_message_details;
    private FirebaseFirestore db;
    private String id;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Bundle extras = getIntent().getExtras();
        String message = extras.getString("message");
        String timeStamp = extras.getString("timeStamp");
        id = extras.getString("id");
        db = FirebaseFirestore.getInstance();
        context = this.getApplicationContext();
        deleteButton = (Button)findViewById(R.id.delete_button);
        textView_message_details =(TextView)findViewById(R.id.message_details_textView);
        textView_message_details.setText("Message details:\nContent: " + message
                + "\nTime stamp: " + timeStamp +"\n ID: " + id);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("messages")
                        .document(id)
                        .delete();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
