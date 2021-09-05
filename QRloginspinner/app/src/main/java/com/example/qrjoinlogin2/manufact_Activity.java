package com.example.qrjoinlogin2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class manufact_Activity extends AppCompatActivity {


    private static String IP_ADDRESS = "13.125.60.252";
    private static String TAG = "manufacturer";

    private IntentIntegrator qrScan;
    private String mJsonString;
    String serialnumber, brandName, productName, B_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manu_home);

        //정보 받아오기
        Intent intent = getIntent();
        B_name = intent.getStringExtra("B_name");
        String Admin_name = intent.getStringExtra("Admin_name");
        String addr = intent.getStringExtra("addr");

        TextView hi = (TextView) findViewById(R.id.facHi);    // 관리자 이름 + 님 환영합니다.
        hi.setText(Admin_name + " 님 환영합니다.");
        TextView facName = (TextView) findViewById(R.id.facName);   // 제조사 이름
        TextView facAdmin = (TextView) findViewById(R.id.facAdmin);   // 관리자 이름
        TextView facAddress = (TextView) findViewById(R.id.facAddress);   // 제조업체 주소
        Button QRscan = (Button) findViewById(R.id.facQrscan); // QR스캔 버튼


        //정보 넣기
        hi.setText(Admin_name + " 님 환영합니다.");
        facName.setText(B_name);
        facAdmin.setText(Admin_name);
        facAddress.setText(addr);


        //QR 스캔해서 제품 등록하기
        qrScan = new IntentIntegrator(this);
        QRscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setPrompt("Scanning QR code");
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });

        ImageView logout = (ImageView) findViewById(R.id.logout); // logout 버튼
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manufact_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(manufact_Activity.this, "취소", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(manufact_Activity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try{

                    JSONObject obj = new JSONObject(result.getContents());


                    GetData task = new GetData();
                    task.execute( "http://" + IP_ADDRESS + ":8080/api/getSupply?serialnum=" + obj.getString("serial"), "");

                    serialnumber = obj.getString("serial");
                    brandName = obj.getString("brand");
                    productName = obj.getString("name");




                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    public class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(manufact_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();

                    String line = "";

                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }
    }


    private void showResult(){

        String TAG_JSON = serialnumber;
        String TAG_NAME = "Name";



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);

                if(name.equals("False")){
                    //등록 안된 정보라는 창
                    no_register_Dialog dialog = new no_register_Dialog(manufact_Activity.this, serialnumber);
                    dialog.show();
                }
                else{

                    manu_registerQR_Dialog dialog = new manu_registerQR_Dialog(manufact_Activity.this, productName, brandName, serialnumber, B_name);
                    dialog.show();
                }

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
