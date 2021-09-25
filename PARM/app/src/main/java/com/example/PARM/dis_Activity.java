package com.example.PARM;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
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

public class dis_Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "13.125.60.252";
    private static String TAG = "distrbution";

    private IntentIntegrator qrScan;
    private String mJsonString;
    String serialnumber, brandName, productName, B_name;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_home);

        //툴바
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false); // 기본 제목을 없애기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 (왼쪽)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        //정보 받아오기
        Intent intent = getIntent();
        B_name = intent.getStringExtra("B_name");
        String Admin_name = intent.getStringExtra("Admin_name");
        String addr = intent.getStringExtra("addr");

        TextView hi = (TextView) findViewById(R.id.disHi);    // 관리자 이름 + 님 환영합니다.
        hi.setText(Admin_name + " 님 환영합니다.");
        TextView disName = (TextView) findViewById(R.id.disName);   // 제조사 이름
        TextView disAdmin = (TextView) findViewById(R.id.disAdmin);   // 관리자 이름
        TextView disAddress = (TextView) findViewById(R.id.disAddress);   // 제조업체 주소
        Button QRscan = (Button) findViewById(R.id.disQrButton); // QR스캔 버튼

        //정보 넣기
        hi.setText(Admin_name + " 님 환영합니다.");
        disName.setText(B_name);
        disAdmin.setText(Admin_name);
        disAddress.setText(addr);


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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.list){

                    Intent intent = new Intent(dis_Activity.this, webView.class);
                    startActivity(intent);
                }
                else if(id == R.id.logout){
                    Toast.makeText(dis_Activity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(dis_Activity.this, MainActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }


    //QR 스캔시
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(dis_Activity.this, "취소", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(dis_Activity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
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

            progressDialog = ProgressDialog.show(dis_Activity.this,
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
        String TAG_FACTORY = "Factory";



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String factory = item.getString(TAG_FACTORY);

                if(name.equals("False")){
                    //등록 안된 정보라는 창
                    no_register_Dialog dialog = new no_register_Dialog(dis_Activity.this, serialnumber);
                    dialog.show();
                }
                else if(factory.equals("none")){ // 제조업체에 도착하지 않았을 때
                    not_arrive_Dialog dialog = new not_arrive_Dialog(dis_Activity.this, serialnumber);
                    dialog.show();
                }
                else{ // 등록이 되어있고 제조업체에 도착했음
                    dis_registerQR_Dialog dialog = new dis_registerQR_Dialog(dis_Activity.this, productName, brandName, serialnumber, B_name);
                    dialog.show();
                }

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
