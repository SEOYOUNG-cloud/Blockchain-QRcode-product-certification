package com.example.qrjoinlogin2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class dis_Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "distrbution";

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_home);

        //정보 받아오기
        Intent intent = getIntent();
        String B_name = intent.getStringExtra("B_name");
        String Admin_name = intent.getStringExtra("Admin_name");
        String addr = intent.getStringExtra("addr");

        TextView hi = (TextView) findViewById(R.id.disHi);    // 관리자 이름 + 님 환영합니다.
        hi.setText(Admin_name + " 님 환영합니다.");
        TextView disName = (TextView) findViewById(R.id.disName);   // 제조사 이름
        TextView disAdmin = (TextView) findViewById(R.id.disAdmin);   // 관리자 이름
        TextView disAddress = (TextView) findViewById(R.id.disAddress);   // 제조업체 주소
        TextView disRecentDate = (TextView) findViewById(R.id.disRecentDate);   // 최근 등록일
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
                qrScan.initiateScan();
            }
        });

    }

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


                    //QR 정보를 보내면서 Dialog 띄우기
                    dis_registerQR_Dialog dialog = new dis_registerQR_Dialog(dis_Activity.this, obj.getString("name"), obj.getString("brand"), obj.getString("serial"));
                    dialog.show();



                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
