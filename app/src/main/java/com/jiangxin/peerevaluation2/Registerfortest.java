package com.jiangxin.peerevaluation2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Registerfortest extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText name;
    EditText age;
    Button sign_in;
    Button register;
    String username_string;
    String password_string;
    String name_string;
    String age_string;
    TextView tip;
    boolean name_success;
    boolean psw_success;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psw);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        sign_in = (Button) findViewById(R.id.sign_in);
        register = (Button) findViewById(R.id.register);
        tip = (TextView) findViewById(R.id.tip);
        setTitle("Register");
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registerfortest.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_string = username.getText().toString();
                password_string = password.getText().toString();
                name_string = name.getText().toString();
                age_string = age.getText().toString();
                //username check
                if(!username_string.contains("@")){
                    username.setError("this should be a email address");
                    name_success = false;
                }else {
                    name_success = true;
                }
                //password check
                if(password_string.length()>4){
                    psw_success = true;
                }else{
                    psw_success = false;
                    password.setError("the password should be longer than 4");
                }
                if (psw_success&name_success){
                    new NewUser().execute();
                }
            }
        });

    }


    class NewUser extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://dogj.000webhostapp.com/registefortest.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("password",password_string));
            nameValuePairs.add(new BasicNameValuePair("name",name_string));
            nameValuePairs.add(new BasicNameValuePair("age",age_string));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
               // String info = EntityUtils.toString(entity);


                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    json = sb.toString();
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }

                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(json);
                    Log.d("test",jObj.toString());
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                int success = jObj.getInt(TAG_SUCCESS);
                String message = jObj.getString(TAG_MESSAGE)+success;

                String info = jObj.toString();

                SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();
                sharedata.putString("username",username_string);
                sharedata.commit();

                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){

                        Log.i("test",message);
                        tip.setText(message);
                    }
                });

//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(Registerfortest.this,HomePage.class));

            return null;
        }
    }

}
