package com.example.PARM;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class userID_change_Dialog extends Dialog {

    private static String IP_ADDRESS = "13.125.60.252";
    private Context context;
    public String serial;

    public userID_change_Dialog(Context context, String serial) {
        super(context);
        this.context = context;
        this.serial = serial;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 타이틀바 없애기
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명으로
        setContentView(R.layout.user_change_alert);


        EditText userID = (EditText) findViewById(R.id.change_userID);


        //버튼
        Button registerBtn = (Button) findViewById(R.id.changeID_btn);
        Button CancelBtn = (Button) findViewById(R.id.cancel_btn);

        //등록 버튼
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = userID.getText().toString();

                dismiss();

                try {
                    new JSONTask().execute("http://" + IP_ADDRESS + ":8080/api/setStatusId?productid=" + serial
                            + "&status=true&userid=" + userid);

                    Toast.makeText(getContext(), "유저 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getContext(), "유저 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //등록 취소 버튼
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getContext().getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
