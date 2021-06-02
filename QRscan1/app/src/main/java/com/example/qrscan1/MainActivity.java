package com.example.qrscan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity { // arraylist에 데이터 저장

   private static String IP_ADDRESS = "192.168.75.31";
    private static String TAG = "phpquerytest";

    private static final String TAG_JSON = "user1";
    private static final String TAG_ID = "id";

    private TextView mTextViewResult;
    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    // ListView mListViewList;
    EditText mEditTextSearchKeyword1;
    String mJsonString;

    ////
    private Button buttonScan;
    private TextView textViewName, textViewAddress, textViewResult;
    private IntentIntegrator qrScan;
    ////



    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /////
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        qrScan = new IntentIntegrator(this);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setPrompt("Scanning.....~");
                qrScan.initiateScan();
            }
        });
        //////



        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        //mListViewList = (ListView) findViewById(R.id.listView_main_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       // mEditTextSearchKeyword1 = (EditText) findViewById(R.id.editText_main_searchKeyword1);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();
        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


//        Button button_search = (Button) findViewById(R.id.button_main_search);
//
//        button_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mArrayList.clear();
//                mAdapter.notifyDataSetChanged();
//
//                String Keyword = mEditTextSearchKeyword1.getText().toString();
//                mEditTextSearchKeyword1.setText("");
//
//                GetData task = new GetData();
//
//                task.execute("http://" + IP_ADDRESS + "/query.php", Keyword);
//            }
//        });

        //mArrayList = new ArrayList<>();


//        Button button_all = (Button) findViewById(R.id.button_main_all);
//        button_all.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//
//                mArrayList.clear();
//                mAdapter.notifyDataSetChanged();
//
//                GetData task = new GetData();
//                task.execute("http://" + IP_ADDRESS + "/getjson.php", "");
//            }
//        });

    }

    ////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(MainActivity.this, "취소~!", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(MainActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try{
                    JSONObject obj = new JSONObject(result.getContents());
                    textViewName.setText(obj.getString("id"));
                   // textViewAddress.setText(obj.getString("address"));


                    mArrayList.clear();
                    mAdapter.notifyDataSetChanged();


                    GetData task = new GetData();

                    task.execute("http://" + IP_ADDRESS + "/query.php", obj.getString("id"));



                } catch (JSONException e){
                    e.printStackTrace();
                    textViewResult.setText(result.getContents());
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /////

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if(result == null){
                mTextViewResult.setText(errorString);
            } else{

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params){

            String serverURL = params[0];
            String postParameters = "id=" + params[1];

            // String searchKeyword1 = params[0];

            // String serverURL = "http://192.168.229.1/query.php";
            //String postParameters = "id = " + searchKeyword1;

            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == httpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                } else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e){
                Log.d(TAG, "InsertData : Error", e);
                errorString = e.toString();

                return null;
            }
        }
    }


    private void showResult(){

        String TAG_JSON = "user1";
        String TAG_ID = "id";

        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            //jsonObject.optBoolean("error");
            //JSONObject jsonObject = new JSONObject(mJsonString.substring(mJsonString.indexOf("{"),mJsonString.lastIndexOf("}") +1));
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);

                PersonalData personalData = new PersonalData();
                personalData.setMember_id(id);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }
        } catch(JSONException e){
            Log.d(TAG, "showResult: : ", e);
        }
    }
}