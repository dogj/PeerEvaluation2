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

import java.util.ArrayList;
import java.util.List;

import old.JSONParser;

public class JoinCourse extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    String message;
    String pid;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";


    Button confirm;
    Button check;
    Button cancel;
    TextView textView_course_name_join;
    TextView textView_course_id_join;
    EditText textView_course_code_join;
    String code_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_course);
        confirm= (Button) findViewById(R.id.join_course_confirm);
        check = (Button) findViewById(R.id.course_check);
        cancel = (Button) findViewById(R.id.join_course_cancel);
        textView_course_name_join= (TextView) findViewById(R.id.course_name_join);
        textView_course_id_join = (TextView) findViewById(R.id.course_id_join);
        textView_course_code_join = (EditText) findViewById(R.id.course_code_join);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_get= String.valueOf(textView_course_code_join.getText());
                new joincourse().execute();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_get= String.valueOf(textView_course_code_join.getText());
                new joincourse2().execute();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JoinCourse.this,Course_home.class));
            }
        });


    }




    class joincourse extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("group_code",code_get));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/join_evaluation.php",
                    "POST", nameValuePairs);

            try {
                int success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            try {
                                textView_course_name_join.setText(json.getString("group_name"));
                                textView_course_id_join.setText(json.getString("group_reason"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
//
//                    pid = json.getString(TAG_PID);
//                    GroupData.set_current_user(pid);
//                    // successfully created product
//                    Intent i = new Intent(getApplicationContext(), Course_home.class);
//                    startActivity(i);

                } else {

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){

                            Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    class joincourse2 extends AsyncTask<String,Void,Long> {

        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("group_code",code_get));
            nameValuePairs.add(new BasicNameValuePair("pid", GroupData.get_current_user()));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/join_evaluation2.php",
                    "POST", nameValuePairs);
            try {
                int success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
