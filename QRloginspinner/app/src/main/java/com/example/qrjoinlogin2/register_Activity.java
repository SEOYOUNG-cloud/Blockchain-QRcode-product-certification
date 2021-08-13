package com.example.qrjoinlogin2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;


public class register_Activity extends AppCompatActivity {

   // private static String IP_ADDRESS = "192.168.75.31";
    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "phptest";

    private TextView mTextViewResult;

    private EditText maddid, maddpwd, maddB_name, maddAdmin_name, maddtel, maddemail, maddaddr;
    private EditText daddid, daddpwd, daddB_name, daddAdmin_name, daddtel, daddemail, daddaddr;
    private EditText saddid, saddpwd, saddB_name, saddAdmin_name, saddtel, saddemail, saddaddr;
    private EditText caddid, caddpwd, caddname, caddtel, caddemail, caddaddr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        maddid = (EditText)findViewById(R.id.madd_id);
        maddpwd = (EditText)findViewById(R.id.madd_pwd);
        maddB_name = (EditText)findViewById(R.id.madd_b_name);
        maddAdmin_name = (EditText)findViewById(R.id.madd_admin_name);
        maddtel = (EditText)findViewById(R.id.madd_tel);
        maddemail = (EditText)findViewById(R.id.madd_email);
        maddaddr = (EditText)findViewById(R.id.madd_addr);


        daddid = (EditText)findViewById(R.id.dadd_id);
        daddpwd = (EditText)findViewById(R.id.dadd_pwd);
        daddB_name = (EditText)findViewById(R.id.dadd_b_name);
        daddAdmin_name = (EditText)findViewById(R.id.dadd_admin_name);
        daddtel = (EditText)findViewById(R.id.dadd_tel);
        daddemail = (EditText)findViewById(R.id.dadd_email);
        daddaddr = (EditText)findViewById(R.id.dadd_addr);

        saddid = (EditText)findViewById(R.id.sadd_id);
        saddpwd = (EditText)findViewById(R.id.sadd_pwd);
        saddB_name = (EditText)findViewById(R.id.sadd_b_name);
        saddAdmin_name = (EditText)findViewById(R.id.sadd_admin_name);
        saddtel = (EditText)findViewById(R.id.sadd_tel);
        saddemail = (EditText)findViewById(R.id.sadd_email);
        saddaddr = (EditText)findViewById(R.id.sadd_addr);

        caddid = (EditText)findViewById(R.id.cadd_id);
        caddpwd = (EditText)findViewById(R.id.cadd_pwd);
        caddname = (EditText)findViewById(R.id.cadd_name);
        caddtel = (EditText)findViewById(R.id.cadd_tel);
        caddemail = (EditText)findViewById(R.id.cadd_email);
        caddaddr = (EditText)findViewById(R.id.cadd_addr);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        LinearLayout layoutmanu = (LinearLayout) findViewById(R.id.LayoutManu);
        LinearLayout layoutdis = (LinearLayout) findViewById(R.id.LayoutDis);
        LinearLayout layoushop = (LinearLayout) findViewById(R.id.LayoutShop);
        LinearLayout layoucust = (LinearLayout) findViewById(R.id.LayoutCustomer);

        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.GONE);
                }
                else if(i==1){
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.VISIBLE);

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String id = caddid.getText().toString();
                            String pwd = caddpwd.getText().toString();
                            String name = caddname.getText().toString();
                            String tel = caddtel.getText().toString();
                            String email = caddemail.getText().toString();
                            String addr = caddaddr.getText().toString();

                            InsertData2 task = new InsertData2();
                            task.execute("http://" + IP_ADDRESS + "/cinsert.php", id, pwd, name, tel, email, addr);


                            caddid.setText("");
                            caddpwd.setText("");
                            caddname.setText("");
                            caddtel.setText("");
                            caddemail.setText("");
                            caddaddr.setText("");

                        }
                    });
                }
                else if(i==2){ // manufacture
                    layoutmanu.setVisibility(View.VISIBLE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.GONE);

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String id = maddid.getText().toString();
                            String pwd = maddpwd.getText().toString();
                            String B_name = maddB_name.getText().toString();
                            String Admin_name = maddAdmin_name.getText().toString();
                            String tel = maddtel.getText().toString();
                            String email = maddemail.getText().toString();
                            String addr = maddaddr.getText().toString();

                            InsertData task = new InsertData();
                            task.execute("http://" + IP_ADDRESS + "/minsert.php", id, pwd, B_name, Admin_name, tel, email, addr);


                            maddid.setText("");
                            maddpwd.setText("");
                            maddB_name.setText("");
                            maddAdmin_name.setText("");
                            maddtel.setText("");
                            maddemail.setText("");
                            maddaddr.setText("");

                        }
                    });
                }
                else if(i==3){ // distribution
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.VISIBLE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.GONE);

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String id = daddid.getText().toString();
                            String pwd = daddpwd.getText().toString();
                            String B_name = daddB_name.getText().toString();
                            String Admin_name = daddAdmin_name.getText().toString();
                            String tel = daddtel.getText().toString();
                            String email = daddemail.getText().toString();
                            String addr = daddaddr.getText().toString();

                            InsertData task = new InsertData();
                            task.execute("http://" + IP_ADDRESS + "/dinsert.php", id, pwd, B_name, Admin_name, tel, email, addr);


                            daddid.setText("");
                            daddpwd.setText("");
                            daddB_name.setText("");
                            daddAdmin_name.setText("");
                            daddtel.setText("");
                            daddemail.setText("");
                            daddaddr.setText("");

                        }
                    });
                }

                else if(i==4){
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.VISIBLE);
                    layoucust.setVisibility(View.GONE);

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String id = saddid.getText().toString();
                            String pwd = saddpwd.getText().toString();
                            String B_name = saddB_name.getText().toString();
                            String Admin_name = saddAdmin_name.getText().toString();
                            String tel = saddtel.getText().toString();
                            String email = saddemail.getText().toString();
                            String addr = saddaddr.getText().toString();

                            InsertData task = new InsertData();
                            task.execute("http://" + IP_ADDRESS + "/sinsert.php", id, pwd, B_name, Admin_name, tel, email, addr);


                            saddid.setText("");
                            saddpwd.setText("");
                            saddB_name.setText("");
                            saddAdmin_name.setText("");
                            saddtel.setText("");
                            saddemail.setText("");
                            saddaddr.setText("");

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(register_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);///////////
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String pwd = (String)params[2];
            String B_name = (String)params[3];
            String Admin_name = (String)params[4];
            String tel = (String)params[5];
            String email = (String)params[6];
            String addr = (String)params[7];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&pwd=" + pwd + "&B_name=" + B_name + "&Admin_name=" + Admin_name + "&tel=" + tel + "&email=" + email + "&addr=" + addr;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class InsertData2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(register_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);///////////
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String pwd = (String)params[2];
            String name = (String)params[3];
            String tel = (String)params[4];
            String email = (String)params[5];
            String addr = (String)params[6];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&pwd=" + pwd + "&name=" + name + "&tel=" + tel + "&email=" + email + "&addr=" + addr;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}