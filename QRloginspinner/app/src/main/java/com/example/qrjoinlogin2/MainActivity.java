package com.example.qrjoinlogin2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

   // private static String IP_ADDRESS = "192.168.75.31";
    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "logintest";

    EditText mID, mPassword, dID, dPassword, sID, sPassword, cID, cPassword;
    Button mIdSignInButton, IdSignUpButton, dIdSignInButton, sIdSignInButton, cIdSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set up the login form.
        mID = (EditText) findViewById(R.id.m_editText_main_searchID);
        mPassword = (EditText) findViewById(R.id.m_editText_main_searchPWD);
        dID = (EditText) findViewById(R.id.d_editText_main_searchID);
        dPassword = (EditText) findViewById(R.id.d_editText_main_searchPWD);
        sID = (EditText) findViewById(R.id.s_editText_main_searchID);
        sPassword = (EditText) findViewById(R.id.s_editText_main_searchPWD);
        cID = (EditText) findViewById(R.id.c_editText_main_searchID);
        cPassword = (EditText) findViewById(R.id.c_editText_main_searchPWD);


        // Button
        mIdSignInButton = (Button) findViewById(R.id.m_login_btn); // sign in button
        IdSignUpButton = (Button) findViewById(R.id.join_btn); // sign up button
        dIdSignInButton = (Button) findViewById(R.id.d_login_btn);
        sIdSignInButton = (Button) findViewById(R.id.s_login_btn);
        cIdSignInButton = (Button) findViewById(R.id.c_login_btn);

        LinearLayout layoutmanulogin = (LinearLayout) findViewById(R.id.Layoutmanulogin);
        LinearLayout layoutdislogin = (LinearLayout) findViewById(R.id.Layoutdislogin);
        LinearLayout layoushoplogin = (LinearLayout) findViewById(R.id.Layoutshoplogin);
        LinearLayout layoucustlogin = (LinearLayout) findViewById(R.id.Layoutcustlogin);

        Spinner loginspinner = (Spinner) findViewById(R.id.loginspinner);

        loginspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.GONE);
                }

                else if(i==1){ //customer

                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.VISIBLE);

                    // customer 로그인 버튼 클릭
                    cIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String id = cID.getText().toString();
                            String pwd = cPassword.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if(success){
                                            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();


                                            String id = jsonResponse.getString("id");
                                            String pwd = jsonResponse.getString("pwd");
                                            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            // 로그인 하면서 사용자 정보 넘기기
                                            //intent.putExtra("id", id);
                                            //intent.putExtra("pwd", pwd);
                                            //startActivity(intent);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();

                                            return;
                                        }
                                    } catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };

                            cLoginRequest cloginRequest = new cLoginRequest(id, pwd, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(cloginRequest);

                        }
                    });
                }
                else if(i==2){ // manufacture
                    layoutmanulogin.setVisibility(View.VISIBLE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.GONE);


                    // manufacturer 로그인 버튼 클릭
                    mIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String id = mID.getText().toString();
                            String pwd = mPassword.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if(success){
                                            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();



                                            String B_name = jsonObject.getString("B_name");
                                            String Admin_name = jsonObject.getString("Admin_name");
                                            String addr = jsonObject.getString("addr");


                                            Intent intent = new Intent(MainActivity.this, manufact_Activity.class);

                                            intent.putExtra("B_name",B_name);
                                            intent.putExtra("Admin_name",Admin_name);
                                            intent.putExtra("addr",addr);

                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();

                                            return;
                                        }
                                    } catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };

                            mLoginRequest mloginRequest = new mLoginRequest(id, pwd, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(mloginRequest);

                        }
                    });

                }
                else if(i==3){ // distribution
                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.VISIBLE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.GONE);

                    // distribution 로그인 버튼 클릭
                    dIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String id = dID.getText().toString();
                            String pwd = dPassword.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if(success){
                                            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();



                                            String B_name = jsonResponse.getString("B_name");
                                            String Admin_name = jsonResponse.getString("Admin_name");
                                            String addr = jsonResponse.getString("addr");

                                            Intent intent = new Intent(MainActivity.this, dis_Activity.class);

                                            intent.putExtra("B_name",B_name);
                                            intent.putExtra("Admin_name",Admin_name);
                                            intent.putExtra("addr",addr);

                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();

                                            return;
                                        }
                                    } catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };

                            dLoginRequest dloginRequest = new dLoginRequest(id, pwd, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(dloginRequest);

                        }
                    });
                }

                else if(i==4){   //shop
                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.VISIBLE);
                    layoucustlogin.setVisibility(View.GONE);

                    // shop 로그인 버튼 클릭
                    sIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String id = sID.getText().toString();
                            String pwd = sPassword.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if(success){
                                            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();


                                            String B_name = jsonResponse.getString("B_name");
                                            String Admin_name = jsonResponse.getString("Admin_name");
                                            String addr = jsonResponse.getString("addr");

                                            Intent intent = new Intent(MainActivity.this, shop_Activity.class);

                                            intent.putExtra("B_name",B_name);
                                            intent.putExtra("Admin_name",Admin_name);
                                            intent.putExtra("addr",addr);

                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();

                                            return;
                                        }
                                    } catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };

                            sLoginRequest sloginRequest = new sLoginRequest(id, pwd, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(sloginRequest);

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 회원가입 버튼 클릭
        IdSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register_Activity.class);
                startActivity(intent);
            }
        });

    }


    public static class mLoginRequest extends StringRequest {

        final static private String URL = "http://" + IP_ADDRESS + "/mlogin.php";
        private Map<String, String> parameters;

        public mLoginRequest(String id, String pwd, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            parameters = new HashMap<>();
            parameters.put("id", id);
            parameters.put("pwd", pwd);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parameters;
        }
    }

    public static class dLoginRequest extends StringRequest {

        final static private String URL = "http://" + IP_ADDRESS + "/dlogin.php";
        private Map<String, String> parameters;

        public dLoginRequest(String id, String pwd, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            parameters = new HashMap<>();
            parameters.put("id", id);
            parameters.put("pwd", pwd);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parameters;
        }
    }

    public static class sLoginRequest extends StringRequest {

        final static private String URL = "http://" + IP_ADDRESS + "/slogin.php";
        private Map<String, String> parameters;

        public sLoginRequest(String id, String pwd, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            parameters = new HashMap<>();
            parameters.put("id", id);
            parameters.put("pwd", pwd);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parameters;
        }
    }

    public static class cLoginRequest extends StringRequest {

        final static private String URL = "http://" + IP_ADDRESS + "/clogin.php";
        private Map<String, String> parameters;

        public cLoginRequest(String id, String pwd, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            parameters = new HashMap<>();
            parameters.put("id", id);
            parameters.put("pwd", pwd);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parameters;
        }
    }
}