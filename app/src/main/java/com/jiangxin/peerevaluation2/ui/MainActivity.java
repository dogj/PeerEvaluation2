package com.jiangxin.peerevaluation2.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.model.GroupData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import old.JSONParser;
import old.Registerfortest;

public class MainActivity extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    EditText username;
    EditText password;
    Button sign_in;
    Button register;
    String username_string;
    String password_string;
    Button button;

    String message;
    String pid;
    TextView tip;
    String info;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psw);
        sign_in = (Button) findViewById(R.id.sign_in);
        register = (Button) findViewById(R.id.register);
        tip = (TextView) findViewById(R.id.tip);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Course_home.class));
            }
        });

        SharedPreferences sharedata = getSharedPreferences("groupData", 0);
        String data = sharedata.getString("username",null);
        register.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Registerfortest.class)));

        sign_in.setOnClickListener(v -> {
            username_string = username.getText().toString();
            password_string = password.getText().toString();
            new login().execute();

        });

    }

    class login extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("password",password_string));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/login.php",
                    "POST", nameValuePairs);

            try {
                int success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    pid = json.getString(TAG_PID);
                    GroupData.set_current_user(pid);
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), Course_home.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }


}

