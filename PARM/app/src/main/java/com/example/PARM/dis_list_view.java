package com.example.PARM;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class dis_list_view extends AppCompatActivity {

    public ArrayList<PersonalData> mArrayList;
    public UserAdapter mAdapter;
    public String B_name;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    public String serial_list[] = new String[50];
    private static String TAG = "BusinessListView";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provelist);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false); // 기본 제목을 없애기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();
        mAdapter = new UserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        B_name = intent.getStringExtra("B_name");

        GetData task = new GetData();
        task.execute( "http://" + "13.125.1.15" + ":8080/api/getDeliveryID?delivery=" + B_name, "");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this::onRefresh);



    }
    public void onRefresh() { // listview swipe 했을 때

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + "13.125.1.15" + ":8080/api/getDeliveryID?delivery=" + B_name, "");



        //새로 고침 완
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

       //     progressDialog = ProgressDialog.show(dis_list_view.this,
        //            "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        //    progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }
    }


    private void showResult(){

        String TAG_JSON = B_name;
        String TAG_Serial = "SerialNum";
        String TAG_NAME = "Name";
        String TAG_BRAND ="Brand";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String serial = item.getString(TAG_Serial);
                String brand = item.getString(TAG_BRAND);

                serial_list[i] = serial;


                String p_name[] = {"583571 1X5CG 6775", "660195 17QDT 2582", "443496 DRWAR 9022", "AS2696 B06364 NE798", "AS2756 B06315 NF024", "AS2785 B06505 ND365"};
                String image[] = {"https://media.gucci.com/style/DarkGray_Center_0_0_800x800/1613669409/583571_1X5CG_6775_001_058_0020_Light-GG.jpg", "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1625733904/360_660195_17QDT_2582_001_100_0000_Light-.jpg",
                        "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1626455703/360_443496_DRWAR_9022_001_100_0000_Light-GG-2016.jpg", "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-white-tweed-aged-calfskin-aged-pale-yellow-metal-tweed-aged-calfskin-aged-pale-yellow-metal-packshot-default-as2696b06364ne798-8840481177630.jpg",
                        "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/e_trim:0/shopping-bag-black-white-shearling-lambskin-gold-tone-metal-shearling-lambskin-gold-tone-metal-packshot-default-as2756b06315nf024-8840469807134.jpg",
                        "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-pink-gray-embroidered-wool-tweed-ruthenium-finished-metal-embroidered-wool-tweed-ruthenium-finished-metal-packshot-default-as2785b06505nd365-8840473378846.jpg"};



                PersonalData personalData = new PersonalData();


                for(int j = 0; j < p_name.length; j++){
                    if(p_name[j].equals(name)) {
                        personalData.setMember_image(image[j]);
                        break;
                    }
                    else{
                    }
                }


                personalData.setMember_name(name);
                personalData.setMember_brand(brand);
                personalData.setMember_serial(serial);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
