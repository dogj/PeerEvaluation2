package old;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiangxin.peerevaluation2.R;

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

public class Register extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText name;
    Button sign_in;
    Button register;
    String username_string;
    String password_string;
    String name_string;
    TextView tip;
    boolean name_success;
    boolean psw_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psw);
        name = (EditText) findViewById(R.id.email);
        sign_in = (Button) findViewById(R.id.register_cancel);
        register = (Button) findViewById(R.id.register);
        tip = (TextView) findViewById(R.id.tip);
        setTitle("Register");
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_string = username.getText().toString();
                password_string = password.getText().toString();
                name_string = name.getText().toString();
                //username check

                    new NewUser().execute();

            }
        });

    }


    class NewUser extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://dogj.000webhostapp.com/evaluation/register.php");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",username_string));
            nameValuePairs.add(new BasicNameValuePair("password",password_string));
            nameValuePairs.add(new BasicNameValuePair("email",name_string));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String info = EntityUtils.toString(entity);

                SharedPreferences.Editor sharedata = getSharedPreferences("groupData", 0).edit();
                sharedata.putString("username",username_string);
                sharedata.putString("pid",username_string);
                sharedata.commit();

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

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
 //           startActivity(new Intent(Register.this,Course_home.class));

            return null;
        }
    }

}
