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


public class shop_Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "shop";

    private IntentIntegrator qrScan;
    boolean QRbool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_home);



        //정보 받아오기
        Intent intent = getIntent();
        String B_name = intent.getStringExtra("B_name");
        String Admin_name = intent.getStringExtra("Admin_name");
        String addr = intent.getStringExtra("addr");

        TextView hi = (TextView) findViewById(R.id.shopHi);    // 관리자 이름 + 님 환영합니다.
        hi.setText(Admin_name + " 님 환영합니다.");
        TextView shopName = (TextView) findViewById(R.id.shopName);   // 제조사 이름
        TextView shopAdmin = (TextView) findViewById(R.id.shopAdmin);   // 관리자 이름
        TextView shopAddress = (TextView) findViewById(R.id.shopAddress);   // 제조업체 주소
        TextView shopRecentDate = (TextView) findViewById(R.id.shopRecentDate);   // 최근 등록일
        Button shopQRscan = (Button) findViewById(R.id.shopQrButton); // QR스캔 버튼
        Button salebtn = (Button) findViewById(R.id.saleButton); // 판매완료 + 사용자 저장 버튼

        //정보 넣기
        hi.setText(Admin_name + " 님 환영합니다.");
        shopName.setText(B_name);
        shopAdmin.setText(Admin_name);
        shopAddress.setText(addr);


        //QR 스캔해서 제품 등록하기
        qrScan = new IntentIntegrator(this);
        shopQRscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRbool = true;
                qrScan.setPrompt("Scanning QR code");
                qrScan.initiateScan();
            }
        });

        //판매 완료 버튼
        salebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRbool = false;
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
                Toast.makeText(shop_Activity.this, "취소", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(shop_Activity.this, "스캔완료!", Toast.LENGTH_SHORT).show();

                if(QRbool == true) {

                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        //QR 정보를 보내면서 Dialog 띄우기
                        shop_registerQR_Dialog dialog1 = new shop_registerQR_Dialog(shop_Activity.this, obj.getString("name"), obj.getString("brand"), obj.getString("serial"));
                        dialog1.show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                else{
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        //QR 정보를 보내면서 Dialog 띄우기

                        shop_sale_Dialog dialog2 = new shop_sale_Dialog(shop_Activity.this, obj.getString("name"), obj.getString("brand"), obj.getString("serial"));
                        dialog2.show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

