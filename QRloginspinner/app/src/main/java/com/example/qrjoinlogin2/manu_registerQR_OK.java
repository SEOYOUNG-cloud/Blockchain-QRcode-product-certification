package com.example.qrjoinlogin2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class manu_registerQR_OK extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manu_save);


        Button facHomeButton = (Button) findViewById(R.id.facHomeButton);

        facHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manu_registerQR_OK.this, manufact_Activity.class);
                startActivity(intent);
            }
        });
    }
}
