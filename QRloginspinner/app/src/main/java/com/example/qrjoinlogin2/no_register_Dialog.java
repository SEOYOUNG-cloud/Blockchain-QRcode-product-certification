package com.example.qrjoinlogin2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class no_register_Dialog extends Dialog {

    private static String TAG = "no_register_Product";
    private Context context;
    private String Serial;


    public no_register_Dialog(Context context, String Serial) {
        super(context);
        this.context = context;
        this.Serial = Serial;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 타이틀바 없애기
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명으로
        setContentView(R.layout.no_register_product_alert);




        //버튼
        Button registerCancelBtn = (Button) findViewById(R.id.okBtn);


        //등록 취소 버튼
        registerCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();            }
        });

    }
}
