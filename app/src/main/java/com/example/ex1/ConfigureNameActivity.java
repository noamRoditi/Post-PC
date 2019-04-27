package com.example.ex1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ConfigureNameActivity extends AppCompatActivity {
    private Button button_skip;
    private Button button_name;
    private EditText editText;
    private Context context;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_name);
        context = this.getApplicationContext();
        db = FirebaseFirestore.getInstance();
        db.collection("defaults").document("user").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> documentData = document.getData();
                    String name = (String) documentData.get("name");
                    if (name != null) {
                        Intent intent = new Intent(context, MainActivity.class);

                        intent.putExtra("name", "hello " + name + "!");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        button_skip = (Button)findViewById(R.id.skip_button);
        button_name = (Button)findViewById(R.id.name_button);
        editText = (EditText)findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().length() > 0){
                  button_name.setVisibility(View.VISIBLE);
                }else {
                    button_name.setVisibility(View.INVISIBLE);
                }
            }
        });
        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editText.getText().toString();
                Map<String,Object> name_map = new HashMap<>();
                name_map.put("name",name);
                db.collection("defaults").document("user").set(name_map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.putExtra("name", "hello " + name + "!");
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
