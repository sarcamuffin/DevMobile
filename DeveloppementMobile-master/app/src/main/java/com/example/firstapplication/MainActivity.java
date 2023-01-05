package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onImgBtnClick(View view){
        ImageButton myButton = (ImageButton) findViewById(R.id.imageButtonPhone);
//        Intent intent = new Intent(this, CheckActivity.class);
        TextView send_text1 = findViewById(R.id.editTextInt1);
        TextView send_text2 = findViewById(R.id.editTexteInt);
        String str = send_text1.getText().toString();
        String str2 = send_text2.getText().toString();
        if(str!=null&&str2!=null){
            myButton.setOnClickListener(v -> {
                // get the value which input by user in EditText and convert it to string

                // Create the Intent object of this class Context() to Second_activity class
                Intent intent = new Intent(this, CheckActivity.class);
                // now by putExtra method put the value in key, value pair key is
                // message_key by this key we will receive the value, and put the string
                intent.putExtra("message_key1", str);
                intent.putExtra("message_key2", str2);
                // start the Intent
                startActivity(intent);
            });
        }


    }


    }

//    public void onBtnClick(View view){
//        TextView txtHello=findViewById(R.id.textMessage);
//        TextView txtName=findViewById(R.id.textName);
//        TextView txtEmail=findViewById(R.id.textEmail);
//        EditText editName=findViewById(R.id.editTexteName);
//        EditText editEmail=findViewById(R.id.editTextEmail);
//        //toString is to convert whatever is in the editText to a string that we can use in code
//        txtHello.setText("hello "+editName.getText().toString());
//        txtName.setText("name "+editName.getText().toString());
//        txtEmail.setText("email "+editEmail.getText().toString());
//    }



    //TODO: complete this
