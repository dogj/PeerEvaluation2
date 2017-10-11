package com.jiangxin.peerevaluation2.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.model.GroupData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import old.JSONParser;

public class Main2Activity extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";
    Button confirm;
    Button login_mode;
    EditText invite_code;
    String code;
    String pid;
    String gid;
    String name;
    String group_name;
    int success;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        confirm= (Button) findViewById(R.id.no_login_confirm);
        login_mode = (Button) findViewById(R.id.login_mode);
        invite_code = (EditText) findViewById(R.id.invite_code);
        login_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = invite_code.getText().toString();
                new nologin().execute();
            }
        });


    }

    class nologin extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("invite_code",code));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/nologin.php",
                    "POST", nameValuePairs);

            try {
                success = json.getInt(TAG_SUCCESS);
                name= json.getString("name");
                group_name= json.getString("group_name");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable(){
                @Override
                public void run(){

                    if(GroupData.isDebug()){
                        Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                    }else{
                        if(success==1) {
                            Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main2Activity.this);
            pDialog.setMessage("Connecting to server. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            if (success == 1) {
                Pattern pattern = Pattern.compile("^[-+]?[0-9]");

                for(int i =1; i<code.length()-1;i++){
                    if(pattern.matcher(code.substring(i-1,i)).matches()&&!(pattern.matcher(code.substring(i,i+1)).matches())){
                        pid = code.substring(0,i);
                    }
                    if(!(pattern.matcher(code.substring(i-1,i)).matches())&&pattern.matcher(code.substring(i,i+1)).matches()){
                        gid = code.substring(i,code.length());
                    }
                }

                GroupData.setCurrent_statue(1);
                GroupData.setCurrent_group(gid);
                GroupData.set_current_user(pid);
                GroupData.setCurrent_user_name(name);
                GroupData.setCurrent_group_name(group_name);
                startActivity(new Intent(Main2Activity.this,TaskHome.class));

            } else {
                Toast.makeText(getApplicationContext(),"login code is incorrect, please check it again",Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }

}





