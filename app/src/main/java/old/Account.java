package old;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.ui.Course_home;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class Account extends AppCompatActivity {

    TextView username;
    EditText name;
    EditText age;
    EditText psw;
    TextView friends;
    Button update;
    Button cancel;
    String username_string;
    String password_string;
    String name_string;
    String age_string;
    TextView tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SharedPreferences sharedata = getSharedPreferences("groupData", 0);
        String data = sharedata.getString("username",null);
        username_string = data;
        setTitle("Account");
        data = "Current account = "+data;
        username = (TextView) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.email);
        age = (EditText) findViewById(R.id.age);
        psw = (EditText) findViewById(R.id.psw);
        tip = (TextView) findViewById(R.id.tip);
        update = (Button) findViewById(R.id.register);
        cancel = (Button) findViewById(R.id.register_cancel);
        friends = (TextView) findViewById(R.id.friends);
        setTitle("Account");
        username.setText(data);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_string = psw.getText().toString();
                name_string = name.getText().toString();
                age_string = age.getText().toString();
                new Update().execute();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedata.edit().clear().commit();
                startActivity(new Intent(Account.this,Course_home.class));
            }
        });



    }



    class Update extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://dogj.000webhostapp.com/update.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("password",password_string));
            nameValuePairs.add(new BasicNameValuePair("name",name_string));
            nameValuePairs.add(new BasicNameValuePair("age",age_string));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String info = EntityUtils.toString(entity);



                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        tip.setText(info);
                    }
                });


//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
