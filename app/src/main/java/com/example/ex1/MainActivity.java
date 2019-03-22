package com.example.ex1;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton sendButton;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.plain_text_input);
        textView.setMovementMethod(new ScrollingMovementMethod());
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = editText.getText();
                if (textView.getText() != null)
                    textView.append("\n");
                textView.append(input);
                editText.setText("");
            }
        });

    }
}
