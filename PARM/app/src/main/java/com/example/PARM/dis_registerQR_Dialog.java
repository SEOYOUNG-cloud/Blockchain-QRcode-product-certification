package com.example.PARM;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class dis_registerQR_Dialog extends Dialog {

    IP_ADDRESS ip = new IP_ADDRESS();
    String IP_ADDRESS = ip.IP_ADDRESS;
    private static String TAG = "distribution_register_QR";
    private Context context;
    private String Name, Brand, Serial, disName;
    Intent intent;
    private Bitmap bitmap;
    private String imageurl;

    public dis_registerQR_Dialog(Context context, String Name, String Brand, String Serial, String disName) {
        super(context);
        this.context = context;
        this.Name = Name;
        this.Brand = Brand;
        this.Serial = Serial;
        this.disName = disName;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 타이틀바 없애기
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명으로
        setContentView(R.layout.dis_alert_qr_scan);

        TextView ProductName = (TextView) findViewById(R.id.dis_register_qrName);
        TextView ProductBrand = (TextView) findViewById(R.id.dis_register_qrBrand);
        TextView ProductSerial = (TextView) findViewById(R.id.dis_register_qrSerial);
        ImageView imageView = (ImageView) findViewById(R.id.dis_saleImage);

        ProductName.setText(Name);
        ProductBrand.setText(Brand);
        ProductSerial.setText(Serial);

        //이미지 넣기
        String p_name[] = {"583571 1X5CG 6775", "660195 17QDT 2582", "443496 DRWAR 9022", "AS2696 B06364 NE798", "AS2756 B06315 NF024", "AS2785 B06505 ND365"};
        String image[] = {"https://media.gucci.com/style/DarkGray_Center_0_0_800x800/1613669409/583571_1X5CG_6775_001_058_0020_Light-GG.jpg", "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1625733904/360_660195_17QDT_2582_001_100_0000_Light-.jpg",
                "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1626455703/360_443496_DRWAR_9022_001_100_0000_Light-GG-2016.jpg", "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-white-tweed-aged-calfskin-aged-pale-yellow-metal-tweed-aged-calfskin-aged-pale-yellow-metal-packshot-default-as2696b06364ne798-8840481177630.jpg",
                "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/e_trim:0/shopping-bag-black-white-shearling-lambskin-gold-tone-metal-shearling-lambskin-gold-tone-metal-packshot-default-as2756b06315nf024-8840469807134.jpg",
                "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-pink-gray-embroidered-wool-tweed-ruthenium-finished-metal-embroidered-wool-tweed-ruthenium-finished-metal-packshot-default-as2785b06505nd365-8840473378846.jpg"};


        for(int i=0; i<p_name.length; i++) {
            if (p_name[i].equals(Name))
                imageurl = image[i];
            else{}
        }

        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(imageurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //버튼
        Button registerBtn = (Button) findViewById(R.id.dis_registerbtn);
        Button registerCancelBtn = (Button) findViewById(R.id.dis_registerCancelbtn);


        //등록 버튼
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://" + IP_ADDRESS + ":8080/api/setDelivery?productid=" + Serial
                        +"&delivery=" + disName);

                intent = new Intent(getContext(), dis_registerQR_OK.class);
                getContext().startActivity(intent);

                dismiss();
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

}