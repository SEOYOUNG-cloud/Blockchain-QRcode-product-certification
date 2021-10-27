package com.example.PARM;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class csm_register_userID_Dialog extends Dialog {

    IP_ADDRESS ip = new IP_ADDRESS();
    String IP_ADDRESS = ip.IP_ADDRESS;
    private static String TAG = "customer_register_userID";
    private Context context;
    private String user_ID, Serial;
    Intent intent;

    public csm_register_userID_Dialog(Context context, String user_ID, String Serial) {
        super(context);
        this.context = context;
        this.user_ID = user_ID;
        this.Serial = Serial;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 타이틀바 없애기
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명으로
        setContentView(R.layout.csm_alert_register_complete);

        TextView userID = (TextView) findViewById(R.id.userID);


        userID.setText(user_ID);


        //버튼
        Button register_userID = (Button) findViewById(R.id.register_userID);
        Button registerCancelBtn = (Button) findViewById(R.id.register_Cancel_userID);

        //등록 버튼
        register_userID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getContext().getApplicationContext(), "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                new JSONTask().execute("http://" + IP_ADDRESS + ":8080/api/setStatusId?productid=" + Serial
                        +"&status=true&userid=" + user_ID);

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
