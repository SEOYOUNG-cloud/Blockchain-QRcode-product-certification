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

public class manufact_Activity extends AppCompatActivity {


    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "manufacturer";

    private IntentIntegrator qrScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manu_home);

        //정보 받아오기
        Intent intent = getIntent();
        String B_name = intent.getStringExtra("B_name");
        String Admin_name = intent.getStringExtra("Admin_name");
        String addr = intent.getStringExtra("addr");

        TextView hi = (TextView) findViewById(R.id.facHi);    // 관리자 이름 + 님 환영합니다.
        hi.setText(Admin_name + " 님 환영합니다.");
        TextView facName = (TextView) findViewById(R.id.facName);   // 제조사 이름
        TextView facAdmin = (TextView) findViewById(R.id.facAdmin);   // 관리자 이름
        TextView facAddress = (TextView) findViewById(R.id.facAddress);   // 제조업체 주소
        TextView facRecentDate = (TextView) findViewById(R.id.facRecentDate);   // 최근 등록일
        Button QRscan = (Button) findViewById(R.id.facQrscan); // QR스캔 버튼
        Button QRcreate = (Button) findViewById(R.id.facQrcreate);  // QR생성 버튼

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
                qrScan.initiateScan();
            }
        });

        //QR생성하기 버튼 클릭
        QRcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(manufact_Activity.this, manu_createQR_Activity.class);
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

//                    //생성할건지 확인하는 창으로 QR정보 보내기
//                    Intent intent = new Intent(manufact_Activity.this, manu_registerQR_Dialog.class);
//                    intent.putExtra("serialnum", obj.getString("serialnum"));
//                    intent.putExtra("name", obj.getString("name"));
//                    intent.putExtra("brand", obj.getString("brand"));
//
//                    startActivity(intent);

                    //QR 정보를 보내면서 Dialog 띄우기
                    manu_registerQR_Dialog dialog = new manu_registerQR_Dialog(manufact_Activity.this, obj.getString("name"), obj.getString("brand"), obj.getString("serial"));
                    dialog.show();




                   // new JSONTask().execute("http://" + IP_ADDRESS + ":8080/api/setProduct?serialnum=" + obj.getString("serialnum")
                   //         + "&name=" + obj.getString("name") +"&brand=" + obj.getString("brand"));
                    //http://localhost:8080/api/setProduct?serialnum=B5N6M7L8&name=476433&brand=Gucci



                } catch (JSONException e){
                    e.printStackTrace();
                   // textViewResult.setText(result.getContents());
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
