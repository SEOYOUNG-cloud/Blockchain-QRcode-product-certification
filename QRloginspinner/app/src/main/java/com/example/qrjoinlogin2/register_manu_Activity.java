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
import java.util.ArrayList;


public class register_manu_Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.75.31";
    private static String TAG = "phptest";

    private TextView mTextViewResult;

    private EditText addid, addpwd, addB_name, addAdmin_name, addtel, addemail, addaddr;

    ArrayList arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addid = (EditText)findViewById(R.id.add_id);
        addpwd = (EditText)findViewById(R.id.add_pwd);
        addB_name = (EditText)findViewById(R.id.add_b_name);
        addAdmin_name = (EditText)findViewById(R.id.add_admin_name);
        addtel = (EditText)findViewById(R.id.add_tel);
        addemail = (EditText)findViewById(R.id.add_email);
        addaddr = (EditText)findViewById(R.id.add_addr);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        LinearLayout layoutmanu = (LinearLayout) findViewById(R.id.LayoutManu);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    layoutmanu.setVisibility(View.INVISIBLE);
                }
                else if(i==1){
                    layoutmanu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = addid.getText().toString();
                String pwd = addpwd.getText().toString();
                String B_name = addB_name.getText().toString();
                String Admin_name = addAdmin_name.getText().toString();
                String tel = addtel.getText().toString();
                String email = addemail.getText().toString();
                String addr = addaddr.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", id, pwd, B_name, Admin_name, tel, email, addr);


                addid.setText("");
                addpwd.setText("");
                addB_name.setText("");
                addAdmin_name.setText("");
                addtel.setText("");
                addemail.setText("");
                addaddr.setText("");

            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(register_manu_Activity.this,
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


}