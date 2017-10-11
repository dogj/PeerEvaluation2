package com.jiangxin.peerevaluation2.ui;

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

import old.JSONParser;

public class AddCourse extends AppCompatActivity {

    EditText coursename_input;
    EditText courseid_input;
    Button add_course;
    Button cancel_course;
    String course_name_input;
    String course_id_input;

    JSONParser jsonParser = new JSONParser();
    String message;
    String pid;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        coursename_input= (EditText) findViewById(R.id.course_name_input);
        courseid_input = (EditText) findViewById(R.id.course_id_input);
        add_course = (Button) findViewById(R.id.Question_submit);
        cancel_course = (Button) findViewById(R.id.add_course_cancel);


        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course_name_input= String.valueOf(coursename_input.getText());
                course_id_input = String.valueOf(courseid_input.getText());
                new addcourse().execute();

                startActivity(new Intent(AddCourse.this,Course_home.class));
            }
        });

        cancel_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCourse.this,Course_home.class));
            }
        });

    }


    class addcourse extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("pid",GroupData.get_current_user()));
            nameValuePairs.add(new BasicNameValuePair("group_name",course_name_input));
            nameValuePairs.add(new BasicNameValuePair("group_reason",course_id_input));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/create_evaluation.php",
                    "POST", nameValuePairs);

            try {
                int success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);

                if (success == 1) {
//                    pid = json.getString(TAG_PID);
//                    GroupData.set_current_user(pid);
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
