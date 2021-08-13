package com.example.qrjoinlogin2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class dis_registerQR_OK extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_save);


        Button disHomeButton = (Button) findViewById(R.id.disHomeButton);

        disHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dis_registerQR_OK.this, dis_Activity.class);
                startActivity(intent);
            }
        });
    }
}
