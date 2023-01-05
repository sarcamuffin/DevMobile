package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button fakeEventInfo;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fakeEventInfo = (Button) findViewById(R.id.fakeEventInfo);
        fakeEventInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                broadcastIntent(v);

            }
        });

    }
    public void broadcastIntent(View view){
        Intent intent = new Intent();
        intent.setAction("com.tutorialspoint.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}