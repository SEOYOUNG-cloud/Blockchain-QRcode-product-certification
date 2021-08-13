package com.example.qrjoinlogin2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class shop_sale_Dialog extends Dialog {

    private static String IP_ADDRESS = "3.35.134.164";
    private static String TAG = "sale_register_QR";
    private Context context;
    private String Name, Brand, Serial;
    Intent intent;

    public shop_sale_Dialog(Context context, String Name, String Brand, String Serial) {
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
        setContentView(R.layout.shop_alert_sale);

        TextView ProductName = (TextView) findViewById(R.id.sale_register_qrName);
        TextView ProductBrand = (TextView) findViewById(R.id.sale_register_qrBrand);
        TextView ProductSerial = (TextView) findViewById(R.id.sale_register_qrSerial);
        EditText userID = (EditText) findViewById(R.id.sale_userID_register);

        ProductName.setText(Name);
        ProductBrand.setText(Brand);
        ProductSerial.setText(Serial);

        //버튼
        Button registerBtn = (Button) findViewById(R.id.sale_registerbtn);
        Button registerCancelBtn = (Button) findViewById(R.id.sale_registerCancelbtn);

        //등록 버튼
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = userID.getText().toString();

                dismiss();
                intent = new Intent(getContext(), shop_sale_OK.class);
                getContext().startActivity(intent);

                new JSONTask().execute("http://" + IP_ADDRESS + ":8080/api/setSupply?productid=" + Serial
                        +"&factory=true&userid=" + userid);
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