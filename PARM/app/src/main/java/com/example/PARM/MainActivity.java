package com.example.PARM;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity{

    private static String TAG = "logintest";

    private ServiceApi service;
    EditText mID, mPassword, dID, dPassword, sID, sPassword, cID, cPassword;
    Button mIdSignInButton, IdSignUpButton, dIdSignInButton, sIdSignInButton, cIdSignInButton;
    CheckBox mLogin, dLogin, sLogin, cLogin;
    SharedPreferences mpref, dpref, spref, cpref;
    SharedPreferences.Editor meditor, deditor, seditor, ceditor;
    Boolean mloginChecked = false, dloginChecked = false, sloginChecked = false, cloginChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set up the login form.
        mID = (EditText) findViewById(R.id.m_editText_main_searchID);
        mPassword = (EditText) findViewById(R.id.m_editText_main_searchPWD);
        dID = (EditText) findViewById(R.id.d_editText_main_searchID);
        dPassword = (EditText) findViewById(R.id.d_editText_main_searchPWD);
        sID = (EditText) findViewById(R.id.s_editText_main_searchID);
        sPassword = (EditText) findViewById(R.id.s_editText_main_searchPWD);
        cID = (EditText) findViewById(R.id.c_editText_main_searchID);
        cPassword = (EditText) findViewById(R.id.c_editText_main_searchPWD);
        service = RetrofitClient.getClient().create(ServiceApi.class);


        // Button
        mIdSignInButton = (Button) findViewById(R.id.m_login_btn); // sign in button
        IdSignUpButton = (Button) findViewById(R.id.join_btn); // sign up button
        dIdSignInButton = (Button) findViewById(R.id.d_login_btn);
        sIdSignInButton = (Button) findViewById(R.id.s_login_btn);
        cIdSignInButton = (Button) findViewById(R.id.c_login_btn);

        LinearLayout layoutmanulogin = (LinearLayout) findViewById(R.id.Layoutmanulogin);
        LinearLayout layoutdislogin = (LinearLayout) findViewById(R.id.Layoutdislogin);
        LinearLayout layoushoplogin = (LinearLayout) findViewById(R.id.Layoutshoplogin);
        LinearLayout layoucustlogin = (LinearLayout) findViewById(R.id.Layoutcustlogin);

        mLogin = (CheckBox) findViewById(R.id.mLoginCheck);

        mLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){  // autologin 체크
                    mpref = getSharedPreferences("mpref",  Activity.MODE_PRIVATE);
                    meditor = mpref.edit();
                    mloginChecked = true;
                }
                else{ // augologin 취소
                    mpref = getSharedPreferences("mpref",  Activity.MODE_PRIVATE);
                    meditor = mpref.edit();
                    mloginChecked = false;
                    meditor.clear();
                    meditor.commit();
                }
            }
        });

        mpref = getSharedPreferences("mpref", Activity.MODE_PRIVATE);
        if (mpref.getBoolean("autoLogin", false)) {
            mID.setText(mpref.getString("id", ""));
            mPassword.setText(mpref.getString("pw", ""));
            mLogin.setChecked(true);
        }

        dLogin = (CheckBox) findViewById(R.id.dLoginCheck);
        dLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){  // autologin 체크
                    dpref = getSharedPreferences("dpref",  Activity.MODE_PRIVATE);
                    deditor = dpref.edit();
                    dloginChecked = true;
                }
                else{ // augologin 취소
                    dpref = getSharedPreferences("dpref",  Activity.MODE_PRIVATE);
                    deditor = dpref.edit();
                    dloginChecked = false;
                    deditor.clear();
                    deditor.commit();
                }
            }
        });

        dpref = getSharedPreferences("dpref", Activity.MODE_PRIVATE);
        if (dpref.getBoolean("autoLogin", false)) {
            dID.setText(dpref.getString("id", ""));
            dPassword.setText(dpref.getString("pw", ""));
            dLogin.setChecked(true);
        }

        sLogin = (CheckBox) findViewById(R.id.sLoginCheck);
        sLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){  // autologin 체크
                    spref = getSharedPreferences("spref",  Activity.MODE_PRIVATE);
                    seditor = spref.edit();
                    sloginChecked = true;
                }
                else{ // augologin 취소
                    spref = getSharedPreferences("spref",  Activity.MODE_PRIVATE);
                    seditor = spref.edit();
                    sloginChecked = false;
                    seditor.clear();
                    seditor.commit();
                }
            }
        });

        spref = getSharedPreferences("spref", Activity.MODE_PRIVATE);
        if (spref.getBoolean("autoLogin", false)) {
            sID.setText(spref.getString("id", ""));
            sPassword.setText(spref.getString("pw", ""));
            sLogin.setChecked(true);
        }

        cLogin = (CheckBox) findViewById(R.id.cLoginCheck);
        cLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){  // autologin 체크
                    cpref = getSharedPreferences("cpref",  Activity.MODE_PRIVATE);
                    ceditor = cpref.edit();
                    cloginChecked = true;
                }
                else{ // autologin 취소
                    cpref = getSharedPreferences("cpref",  Activity.MODE_PRIVATE);
                    ceditor = cpref.edit();
                    cloginChecked = false;
                    ceditor.clear();
                    ceditor.commit();
                }
            }
        });

        cpref = getSharedPreferences("cpref", Activity.MODE_PRIVATE);
        if (cpref.getBoolean("autoLogin", false)) {
            cID.setText(cpref.getString("id", ""));
            cPassword.setText(cpref.getString("pw", ""));
            cLogin.setChecked(true);
        }


        Spinner loginspinner = (Spinner) findViewById(R.id.loginspinner);

        loginspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){ //customer

                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.VISIBLE);

                    // customer 로그인 버튼 클릭
                    cIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attempt_cLogin();
                        }
                    });
                }
                else if(i==1){ // manufacture
                    layoutmanulogin.setVisibility(View.VISIBLE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.GONE);

                    // manufacturer 로그인 버튼 클릭
                    mIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attempt_mLogin();
                        }
                    });

                }
                else if(i==2){ // distribution
                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.VISIBLE);
                    layoushoplogin.setVisibility(View.GONE);
                    layoucustlogin.setVisibility(View.GONE);

                    // distribution 로그인 버튼 클릭
                    dIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attempt_dLogin();
                        }
                    });
                }

                else if(i==3){   //shop
                    layoutmanulogin.setVisibility(View.GONE);
                    layoutdislogin.setVisibility(View.GONE);
                    layoushoplogin.setVisibility(View.VISIBLE);
                    layoucustlogin.setVisibility(View.GONE);

                    // shop 로그인 버튼 클릭
                    sIdSignInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attempt_sLogin();
                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 회원가입 버튼 클릭
        IdSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void attempt_cLogin() {
        cID.setError(null);
        cPassword.setError(null);

        String userid = cID.getText().toString();
        String password = cPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            cID.setError("비밀번호를 입력해주세요.");
            focusView = cID;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            cPassword.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = cPassword;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            start_cLogin(new LoginData(userid, password));
        }
    }

    private void start_cLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(result.getResult()) {
                    Intent intent = new Intent(MainActivity.this, csm_Activity.class);
                    intent.putExtra("name", result.getName());
                    intent.putExtra("id", result.getId());
                    startActivity(intent);

                    if(cloginChecked) {
                        SharedPreferences.Editor ceditor = cpref.edit();
                        ceditor.putString("id", result.getId());
                        ceditor.putString("pw", result.getPwd());
                        ceditor.putBoolean("autoLogin", true);
                        ceditor.commit();
                    }else{}
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void attempt_mLogin() {
        mID.setError(null);
        mPassword.setError(null);

        String userid = mID.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mID.setError("비밀번호를 입력해주세요.");
            focusView = mID;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPassword.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = mPassword;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            start_mLogin(new LoginData(userid, password));
        }
    }

    private void start_mLogin(LoginData data) {
        service.manuLogin(data).enqueue(new Callback<LoginResponse2>() {
            @Override
            public void onResponse(Call<LoginResponse2> call, retrofit2.Response<LoginResponse2> response) {
                LoginResponse2 result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(result.getMessage().equals("로그인 성공")) {
                    Intent intent = new Intent(MainActivity.this, manufact_Activity.class);
                    intent.putExtra("B_name",result.getB_Name());
                    intent.putExtra("Admin_name",result.getA_Name());
                    intent.putExtra("addr",result.getAddr());
                    startActivity(intent);

                    if(mloginChecked) {
                        SharedPreferences.Editor meditor = mpref.edit();
                        meditor.putString("id", result.getId());
                        meditor.putString("pw", result.getPwd());
                        meditor.putBoolean("autoLogin", true);
                        meditor.commit();
                    }
                } else{}
            }

            @Override
            public void onFailure(Call<LoginResponse2> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void attempt_dLogin() {
        dID.setError(null);
        dPassword.setError(null);

        String userid = dID.getText().toString();
        String password = dPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            dID.setError("비밀번호를 입력해주세요.");
            focusView = dID;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            dPassword.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = dPassword;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            start_dLogin(new LoginData(userid, password));
        }
    }

    private void start_dLogin(LoginData data) {
        service.disLogin(data).enqueue(new Callback<LoginResponse2>() {
            @Override
            public void onResponse(Call<LoginResponse2> call, retrofit2.Response<LoginResponse2> response) {
                LoginResponse2 result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(result.getMessage().equals("로그인 성공")) {
                    Intent intent = new Intent(MainActivity.this, dis_Activity.class);
                    intent.putExtra("B_name",result.getB_Name());
                    intent.putExtra("Admin_name",result.getA_Name());
                    intent.putExtra("addr",result.getAddr());
                    startActivity(intent);

                    if(dloginChecked) {
                        SharedPreferences.Editor deditor = dpref.edit();
                        deditor.putString("id", result.getId());
                        deditor.putString("pw", result.getPwd());
                        deditor.putBoolean("autoLogin", true);
                        deditor.commit();
                    }
                } else{}
            }

            @Override
            public void onFailure(Call<LoginResponse2> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void attempt_sLogin() {
        sID.setError(null);
        sPassword.setError(null);

        String userid = sID.getText().toString();
        String password = sPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            sID.setError("비밀번호를 입력해주세요.");
            focusView = sID;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            sPassword.setError("5자 이상의 비밀번호를 입력해주세요.");
            focusView = sPassword;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            start_sLogin(new LoginData(userid, password));
        }
    }

    private void start_sLogin(LoginData data) {
        service.shopLogin(data).enqueue(new Callback<LoginResponse2>() {
            @Override
            public void onResponse(Call<LoginResponse2> call, retrofit2.Response<LoginResponse2> response) {
                LoginResponse2 result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(result.getMessage().equals("로그인 성공")) {
                    Intent intent = new Intent(MainActivity.this, shop_Activity.class);
                    intent.putExtra("B_name",result.getB_Name());
                    intent.putExtra("Admin_name",result.getA_Name());
                    intent.putExtra("addr",result.getAddr());
                    startActivity(intent);

                    if(sloginChecked) {
                        SharedPreferences.Editor seditor = spref.edit();
                        seditor.putString("id", result.getId());
                        seditor.putString("pw", result.getPwd());
                        seditor.putBoolean("autoLogin", true);
                        seditor.commit();
                    }
                } else{

                }
            }

            @Override
            public void onFailure(Call<LoginResponse2> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }


    private boolean isPasswordValid(String password) {
        return password.length() >= 5;
    }

}