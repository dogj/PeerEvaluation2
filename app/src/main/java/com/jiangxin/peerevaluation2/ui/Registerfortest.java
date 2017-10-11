package com.jiangxin.peerevaluation2.ui;

import android.content.Intent;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import old.JSONParser;

public class Registerfortest extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    EditText username;
    EditText password;
    EditText email;
    Button sign_in;
    Button register;
    String username_string;
    String password_string;
    String mail_string;
    String message;
    String pid;

    TextView tip;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psw);
        email = (EditText) findViewById(R.id.email);

        register = (Button) findViewById(R.id.register);
        tip = (TextView) findViewById(R.id.tip);
        setTitle("Register");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_string = username.getText().toString();
                password_string = password.getText().toString();
                mail_string = email.getText().toString();
                new NewUser().execute();

            }
        });

    }


    class NewUser extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("password",password_string));
            nameValuePairs.add(new BasicNameValuePair("email",mail_string));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/registefortest.php",
                    "POST", nameValuePairs);

            try {
                int success = json.getInt(TAG_SUCCESS);
                 message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    pid = json.getString(TAG_PID);
                    GroupData.setCurrent_statue(0);
                    GroupData.set_current_user(pid);
                    GroupData.setCurrent_user_name(username_string);


                    Intent i = new Intent(getApplicationContext(), Course_home.class);
                    startActivity(i);

                } else {
//                    Intent i = new Intent(getApplicationContext(), Registerfortest.class);
//                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(GroupData.isDebug()){
                            Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            return null;
        }
    }

}
