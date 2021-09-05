package com.example.qrjoinlogin2;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class listview_item_Activity extends AppCompatActivity {

    private static String IP_ADDRESS = "13.125.60.252";
    private static String TAG = "item_info";
    public ArrayList<ProductData> mArrayList;
    public ProductAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    public String serial;
    Bitmap bitmap;
    private String imageurl;
    TextView productName, productBrand, productSerial;
    ImageView productImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.csm_cert);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false); // 기본 제목을 없애기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼


        Intent intent = getIntent();
        serial = intent.getStringExtra("serial"); // 시리얼 받아옴

        mRecyclerView = (RecyclerView) findViewById(R.id.View_product_info);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();
        mAdapter = new ProductAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        productName = (TextView) findViewById(R.id.Product_Name);
        productBrand = (TextView) findViewById(R.id.Product_Brand);
        productSerial = (TextView) findViewById(R.id.Product_Serial);
        productImage = (ImageView) findViewById(R.id.ProductImage);


        productSerial.setText(serial);


        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + ":8080/api/getSupply?serialnum=" + serial, "");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.csm_manu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.change: // 사용자 변경 클릭
                userID_change_Dialog dialog = new userID_change_Dialog(listview_item_Activity.this, serial);
                dialog.show();
                break;
            case R.id.share: // 공유하기 클릭

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("CODE", "http://13.125.60.252:8000/");
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home: // 뒤로가기
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    //Json data 읽어오기
    public class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(listview_item_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

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
                JSONObject jsonObject = new JSONObject();


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";

                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }
    }


    private void showResult(){

        String TAG_JSON = serial;
        String TAG_NAME = "Name";
        String TAG_BRAND ="Brand";
        String TAG_FACTORY ="Factory";
        String TAG_DELIVERY ="Delivery";
        String TAG_STORE ="Store";



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String brand = item.getString(TAG_BRAND);
                String factory = item.getString(TAG_FACTORY);
                String delivery = item.getString(TAG_DELIVERY);
                String store = item.getString(TAG_STORE);


                productName.setText(name);
                productBrand.setText(brand);



                String p_name[] = {"583571 1X5CG 6775", "660195 17QDT 2582", "443496 DRWAR 9022", "AS2696 B06364 NE798", "AS2756 B06315 NF024", "AS2785 B06505 ND365"};
                String image[] = {"https://media.gucci.com/style/DarkGray_Center_0_0_800x800/1613669409/583571_1X5CG_6775_001_058_0020_Light-GG.jpg", "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1625733904/360_660195_17QDT_2582_001_100_0000_Light-.jpg",
                        "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1626455703/360_443496_DRWAR_9022_001_100_0000_Light-GG-2016.jpg", "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-white-tweed-aged-calfskin-aged-pale-yellow-metal-tweed-aged-calfskin-aged-pale-yellow-metal-packshot-default-as2696b06364ne798-8840481177630.jpg",
                        "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/e_trim:0/shopping-bag-black-white-shearling-lambskin-gold-tone-metal-shearling-lambskin-gold-tone-metal-packshot-default-as2756b06315nf024-8840469807134.jpg",
                        "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-pink-gray-embroidered-wool-tweed-ruthenium-finished-metal-embroidered-wool-tweed-ruthenium-finished-metal-packshot-default-as2785b06505nd365-8840473378846.jpg"};


                for(int j = 0; j < p_name.length; j++){
                    if(p_name[j].equals(name)) {
                        imageurl = image[j];
                        break;
                    }
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
                    productImage.setImageBitmap(bitmap);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }


                ProductData productdata = new ProductData();

                productdata.setmanu_name(factory);
                productdata.setdis_name(delivery);
                productdata.setshop_name(store);

                mArrayList.add(productdata);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


}
