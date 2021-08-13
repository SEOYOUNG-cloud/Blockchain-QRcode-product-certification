package com.example.qrjoinlogin2;

import android.app.Activity;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class manu_registerQR_Dialog extends Dialog {

    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "manufacturer_register_QR";
    private Context context;
    private String Name, Brand, Serial;
    Intent intent;

    public manu_registerQR_Dialog(Context context, String Name, String Brand, String Serial) {
        super(context);
        this.context = context;
        this.Name = Name;
        this.Brand = Brand;
        this.Serial = Serial;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 타이틀바 없애기
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명으로
        setContentView(R.layout.manu_alert_qr_scan);

        TextView ProductName = (TextView) findViewById(R.id.register_qrName);
        TextView ProductBrand = (TextView) findViewById(R.id.register_qrBrand);
        TextView ProductSerial = (TextView) findViewById(R.id.register_qrSerial);

        ProductName.setText(Name);
        ProductBrand.setText(Brand);
        ProductSerial.setText(Serial);

        //버튼
        Button registerBtn = (Button) findViewById(R.id.registerbtn);
        Button registerCancelBtn = (Button) findViewById(R.id.registerCancelbtn);

        //등록 버튼
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                 intent = new Intent(getContext(), manu_registerQR_OK.class);
                 getContext().startActivity(intent);

                new JSONTask().execute("http://" + IP_ADDRESS + ":8080/api/setProduct?serialnum=" + Serial
                                 + "&name=" + Name +"&brand=" + Brand);
            }
        });

        //등록 취소 버튼
        registerCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getContext().getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });




    }

//    public class JSONTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try {
//                    URL url = new URL(urls[0]);
//                    con = (HttpURLConnection) url.openConnection();
//                    con.connect();
//
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while ((line = reader.readLine()) != null) {
//                        buffer.append(line);
//                    }
//
//                    return buffer.toString();
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (con != null) {
//                        con.disconnect();
//                    }
//                    try {
//                        if (reader != null) {
//                            reader.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//    }
}