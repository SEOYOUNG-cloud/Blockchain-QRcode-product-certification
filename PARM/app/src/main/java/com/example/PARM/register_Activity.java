package com.example.PARM;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;


public class register_Activity extends AppCompatActivity {

    private static String TAG = "signup";

    private ServiceApi service;
    private TextView mTextViewResult;

    private EditText maddid, maddpwd, maddB_name, maddAdmin_name, maddtel, maddemail, maddaddr;
    private EditText daddid, daddpwd, daddB_name, daddAdmin_name, daddtel, daddemail, daddaddr;
    private EditText saddid, saddpwd, saddB_name, saddAdmin_name, saddtel, saddemail, saddaddr;
    private EditText caddid, caddpwd, caddname, caddtel, caddemail, caddaddr;

    boolean cChecked, mChecked, dChecked, sChecked = false; // 아이디 중복체크 여부


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        maddid = (EditText)findViewById(R.id.madd_id);
        maddpwd = (EditText)findViewById(R.id.madd_pwd);
        maddB_name = (EditText)findViewById(R.id.madd_b_name);
        maddAdmin_name = (EditText)findViewById(R.id.madd_admin_name);
        maddtel = (EditText)findViewById(R.id.madd_tel);
        maddemail = (EditText)findViewById(R.id.madd_email);
        maddaddr = (EditText)findViewById(R.id.madd_addr);
        maddB_name.setFilters(new InputFilter[]{filter});
        service = RetrofitClient.getClient().create(ServiceApi.class);


        daddid = (EditText)findViewById(R.id.dadd_id);
        daddpwd = (EditText)findViewById(R.id.dadd_pwd);
        daddB_name = (EditText)findViewById(R.id.dadd_b_name);
        daddAdmin_name = (EditText)findViewById(R.id.dadd_admin_name);
        daddtel = (EditText)findViewById(R.id.dadd_tel);
        daddemail = (EditText)findViewById(R.id.dadd_email);
        daddaddr = (EditText)findViewById(R.id.dadd_addr);
        daddB_name.setFilters(new InputFilter[]{filter});

        saddid = (EditText)findViewById(R.id.sadd_id);
        saddpwd = (EditText)findViewById(R.id.sadd_pwd);
        saddB_name = (EditText)findViewById(R.id.sadd_b_name);
        saddAdmin_name = (EditText)findViewById(R.id.sadd_admin_name);
        saddtel = (EditText)findViewById(R.id.sadd_tel);
        saddemail = (EditText)findViewById(R.id.sadd_email);
        saddaddr = (EditText)findViewById(R.id.sadd_addr);
        saddB_name.setFilters(new InputFilter[]{filter});

        caddid = (EditText)findViewById(R.id.cadd_id);
        caddpwd = (EditText)findViewById(R.id.cadd_pwd);
        caddname = (EditText)findViewById(R.id.cadd_name);
        caddtel = (EditText)findViewById(R.id.cadd_tel);
        caddemail = (EditText)findViewById(R.id.cadd_email);
        caddaddr = (EditText)findViewById(R.id.cadd_addr);


        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        LinearLayout layoutmanu = (LinearLayout) findViewById(R.id.LayoutManu);
        LinearLayout layoutdis = (LinearLayout) findViewById(R.id.LayoutDis);
        LinearLayout layoushop = (LinearLayout) findViewById(R.id.LayoutShop);
        LinearLayout layoucust = (LinearLayout) findViewById(R.id.LayoutCustomer);

        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        Button mbuttonvalidate = (Button)findViewById(R.id.midvalidatebtn);
        Button dbuttonvalidate = (Button)findViewById(R.id.didvalidatebtn);
        Button sbuttonvalidate = (Button)findViewById(R.id.sidvalidatebtn);
        Button cbuttonvalidate = (Button)findViewById(R.id.cidvalidatebtn);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){ // 소비자
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.VISIBLE);

                    cbuttonvalidate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validatecID(new checkidData(caddid.getText().toString()));

                        }
                    });

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!cChecked) Toast.makeText(getApplicationContext(),"중복체크를 해주세요",Toast.LENGTH_SHORT).show();
                            else
                                attemptJoin();

                        }
                    });
                }
                else if(i==1){ // manufacture
                    layoutmanu.setVisibility(View.VISIBLE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.GONE);

                    mbuttonvalidate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validatemID(new checkidData(maddid.getText().toString()));

                          }
                    });

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!mChecked) Toast.makeText(getApplicationContext(),"중복체크를 해주세요",Toast.LENGTH_SHORT).show();
                            else
                                attemptmJoin();
                        }
                    });
                }
                else if(i==2){ // distribution
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.VISIBLE);
                    layoushop.setVisibility(View.GONE);
                    layoucust.setVisibility(View.GONE);

                    dbuttonvalidate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validatedID(new checkidData(daddid.getText().toString()));
                        }
                    });

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!dChecked) Toast.makeText(getApplicationContext(),"중복체크를 해주세요",Toast.LENGTH_SHORT).show();
                            else
                             attemptdJoin();
                        }
                    });
                }

                else if(i==3){ // shop
                    layoutmanu.setVisibility(View.GONE);
                    layoutdis.setVisibility(View.GONE);
                    layoushop.setVisibility(View.VISIBLE);
                    layoucust.setVisibility(View.GONE);

                    sbuttonvalidate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validatesID(new checkidData(saddid.getText().toString()));
                        }
                    });

                    buttonInsert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!sChecked) Toast.makeText(getApplicationContext(),"중복체크를 해주세요",Toast.LENGTH_SHORT).show();
                            else
                                attemptsJoin();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    public InputFilter filter= new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,

                                   Spanned dest, int dstart, int dend) {


            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if (!ps.matcher(source).matches()) {

                return "";

            }

            return null;

        }

    };
    /////////////////////////////////////////////////
    private void attemptJoin() {
        caddid.setError(null);
        caddpwd.setError(null);
        caddname.setError(null);
        caddtel.setError(null);
        caddemail.setError(null);
        caddaddr.setError(null);

        String id = caddid.getText().toString();
        String pwd = caddpwd.getText().toString();
        String name = caddname.getText().toString();
        String tel = caddtel.getText().toString();
        String email = caddemail.getText().toString();
        String addr = caddaddr.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (pwd.isEmpty()) {
            caddpwd.setError("비밀번호를 입력해주세요.");
            focusView = caddpwd;
            cancel = true;
        } else if (!isPasswordValid(pwd)) {
            caddpwd.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = caddpwd;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            caddemail.setError("이메일을 입력해주세요.");
            focusView = caddemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            caddemail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = caddemail;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            caddname.setError("이름을 입력해주세요.");
            focusView = caddname;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinData_customer(id, pwd, name, tel, email, addr));
        }
    }

    private void startJoin(JoinData_customer data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, retrofit2.Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(register_Activity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    private void attemptmJoin() {
        maddid.setError(null);
        maddpwd.setError(null);
        maddB_name.setError(null);
        maddAdmin_name.setError(null);
        maddtel.setError(null);
        maddemail.setError(null);
        maddaddr.setError(null);

        String id = maddid.getText().toString();
        String pwd = maddpwd.getText().toString();
        String B_name = maddB_name.getText().toString();
        String Admin_name = maddAdmin_name.getText().toString();
        String tel = maddtel.getText().toString();
        String email = maddemail.getText().toString();
        String addr = maddaddr.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (pwd.isEmpty()) {
            maddpwd.setError("비밀번호를 입력해주세요.");
            focusView = maddpwd;
            cancel = true;
        } else if (!isPasswordValid(pwd)) {
            maddpwd.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = maddpwd;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            maddemail.setError("이메일을 입력해주세요.");
            focusView = maddemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            maddemail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = maddemail;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (Admin_name.isEmpty()) {
            maddAdmin_name.setError("관리자 이름을 입력해주세요.");
            focusView = maddAdmin_name;
            cancel = true;
        }
        if (B_name.isEmpty()) {
            maddB_name.setError("업체 이름을 입력해주세요.");
            focusView = maddB_name;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startmJoin(new JoinData_(id, pwd, B_name, Admin_name, tel, email, addr));
        }
    }

    private void startmJoin(JoinData_ data) {
        service.manuJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, retrofit2.Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(register_Activity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    private void attemptdJoin() {
        daddid.setError(null);
        daddpwd.setError(null);
        daddB_name.setError(null);
        daddAdmin_name.setError(null);
        daddtel.setError(null);
        daddemail.setError(null);
        daddaddr.setError(null);

        String id = daddid.getText().toString();
        String pwd = daddpwd.getText().toString();
        String B_name = daddB_name.getText().toString();
        String Admin_name = daddAdmin_name.getText().toString();
        String tel = daddtel.getText().toString();
        String email = daddemail.getText().toString();
        String addr = daddaddr.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (pwd.isEmpty()) {
            daddpwd.setError("비밀번호를 입력해주세요.");
            focusView = daddpwd;
            cancel = true;
        } else if (!isPasswordValid(pwd)) {
            daddpwd.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = daddpwd;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            daddemail.setError("이메일을 입력해주세요.");
            focusView = daddemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            daddemail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = daddemail;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (Admin_name.isEmpty()) {
            daddAdmin_name.setError("관리자 이름을 입력해주세요.");
            focusView = daddAdmin_name;
            cancel = true;
        }
        if (B_name.isEmpty()) {
            daddB_name.setError("업체 이름을 입력해주세요.");
            focusView = daddB_name;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startdJoin(new JoinData_(id, pwd, B_name, Admin_name, tel, email, addr));
        }
    }

    private void startdJoin(JoinData_ data) {
        service.disJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, retrofit2.Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(register_Activity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    private void attemptsJoin() {
        saddid.setError(null);
        saddpwd.setError(null);
        saddB_name.setError(null);
        saddAdmin_name.setError(null);
        saddtel.setError(null);
        saddemail.setError(null);
        saddaddr.setError(null);

        String id = saddid.getText().toString();
        String pwd = saddpwd.getText().toString();
        String B_name = saddB_name.getText().toString();
        String Admin_name = saddAdmin_name.getText().toString();
        String tel = saddtel.getText().toString();
        String email = saddemail.getText().toString();
        String addr = saddaddr.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (pwd.isEmpty()) {
            saddpwd.setError("비밀번호를 입력해주세요.");
            focusView = saddpwd;
            cancel = true;
        } else if (!isPasswordValid(pwd)) {
            saddpwd.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = saddpwd;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            saddemail.setError("이메일을 입력해주세요.");
            focusView = saddemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            saddemail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = saddemail;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (Admin_name.isEmpty()) {
            saddAdmin_name.setError("관리자 이름을 입력해주세요.");
            focusView = saddAdmin_name;
            cancel = true;
        }
        if (B_name.isEmpty()) {
            saddB_name.setError("업체 이름을 입력해주세요.");
            focusView = saddB_name;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startsJoin(new JoinData_(id, pwd, B_name, Admin_name, tel, email, addr));
        }
    }

    private void startsJoin(JoinData_ data) {
        service.shopJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, retrofit2.Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(register_Activity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 5;
    }

//////////////////// 중복확인
    private void validatecID(checkidData data) {
        service.checkID(data).enqueue(new Callback<Join_checkJD>() {
            @Override
            public void onResponse(Call<Join_checkJD> call, retrofit2.Response<Join_checkJD> response) {
                Join_checkJD result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(!result.getResult()) {
                    cChecked = true;
                    caddid.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                    caddid.setTextColor(getResources().getColor(R.color.gray));
                } else{}
            }

            @Override
            public void onFailure(Call<Join_checkJD> call, Throwable t) {
                Toast.makeText(register_Activity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void validatemID(checkidData data) {
        service.checkmID(data).enqueue(new Callback<Join_checkJD>() {
            @Override
            public void onResponse(Call<Join_checkJD> call, retrofit2.Response<Join_checkJD> response) {
                Join_checkJD result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(!result.getResult()) {
                    mChecked = true;
                    maddid.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                    maddid.setTextColor(getResources().getColor(R.color.gray));
                } else{}
            }

            @Override
            public void onFailure(Call<Join_checkJD> call, Throwable t) {
                Toast.makeText(register_Activity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void validatedID(checkidData data) {
        service.checkdID(data).enqueue(new Callback<Join_checkJD>() {
            @Override
            public void onResponse(Call<Join_checkJD> call, retrofit2.Response<Join_checkJD> response) {
                Join_checkJD result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(!result.getResult()) {
                    dChecked = true;
                    daddid.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                    daddid.setTextColor(getResources().getColor(R.color.gray));
                } else{}
            }

            @Override
            public void onFailure(Call<Join_checkJD> call, Throwable t) {
                Toast.makeText(register_Activity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void validatesID(checkidData data) {
        service.checksID(data).enqueue(new Callback<Join_checkJD>() {
            @Override
            public void onResponse(Call<Join_checkJD> call, retrofit2.Response<Join_checkJD> response) {
                Join_checkJD result = response.body();
                Toast.makeText(register_Activity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(!result.getResult()) {
                    sChecked = true;
                    saddid.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                    saddid.setTextColor(getResources().getColor(R.color.gray));
                } else{}
            }

            @Override
            public void onFailure(Call<Join_checkJD> call, Throwable t) {
                Toast.makeText(register_Activity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

}