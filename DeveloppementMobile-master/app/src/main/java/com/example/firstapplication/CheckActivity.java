package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {


    TextView receiver_msg;
    TextView receiver_msg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        receiver_msg = findViewById(R.id.textViewChaleng1);
        receiver_msg2 = findViewById(R.id.textViewChlng2);
        // create the get Intent object
        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        String str = intent.getStringExtra("message_key1");
        String str2 = intent.getStringExtra("message_key2");
        // display the string into textView
        receiver_msg.setText(str);
        receiver_msg2.setText(str2);


    }

    public void cancel(){
        Button myButton =  findViewById(R.id.buttonCancel);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        String text = "l’opération a été annulée";
// toast time duration, can also set manual value
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(CheckActivity.this, text, duration);
// to show the toast
        toast.show();

    }
}