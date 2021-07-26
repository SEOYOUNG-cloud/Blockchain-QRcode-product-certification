package com.example.qrjoinlogin2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity{

    private static String IP_ADDRESS = "192.168.75.31";
    private static String TAG = "logintest";

    EditText mID, mPassword;
    Button mIdSignInButton, mIdSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the login form.
        mID = (EditText) findViewById(R.id.editText_main_searchID);
        mPassword = (EditText) findViewById(R.id.editText_main_searchPWD);

        // Button
        mIdSignInButton = (Button) findViewById(R.id.login_btn); // sign in button
        mIdSignUpButton = (Button) findViewById(R.id.join_btn); // sign up button


        // 로그인 버튼 클릭
        mIdSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mID.getText().toString();
                String pwd = mPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                                Log.v("test", "login success");

                                String id = jsonResponse.getString("id");
                                String pwd = jsonResponse.getString("pwd");
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                // 로그인 하면서 사용자 정보 넘기기
                                //intent.putExtra("id", id);
                                //intent.putExtra("pwd", pwd);
                                //startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                Log.v("ttest", "login xxXXXxxXX1");

                                return;
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(id, pwd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

        // 회원가입 버튼 클릭
        mIdSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registerActicity.class);
                startActivity(intent);
            }
        });

    }
}