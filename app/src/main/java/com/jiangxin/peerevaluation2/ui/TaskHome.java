package com.jiangxin.peerevaluation2.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.adapter.Question_Adapter;
import com.jiangxin.peerevaluation2.model.AnswerData;
import com.jiangxin.peerevaluation2.model.AnswerItem;
import com.jiangxin.peerevaluation2.model.GroupData;
import com.jiangxin.peerevaluation2.model.QuestionData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import old.JSONParser;

public class TaskHome extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    String message;
    String pid;
    JSONArray jsonArray;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";
    JSONArray course_list = null;
    private ProgressDialog pDialog;
    Button add_new;
    JSONArray groups = null;
    JSONArray message = null;
    int length;


    String position;
    int item_position;
    Button submit;
    Button cancel;
    Question_Adapter adapter;
    QuestionData questionData;
    TextView test;
    AnswerItem fortest;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_home);

        recyclerView = (RecyclerView) findViewById(R.id.rec_task_home);
        submit = (Button) findViewById(R.id.Question_submit);
        test= (TextView) findViewById(R.id.question_test);
        test.setText(GroupData.getCurrent_group());
        cancel= (Button) findViewById(R.id.task_home_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskHome.this,Course_home.class));
            }
        });
        new getQuestion().execute();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Question_Adapter(QuestionData.getListData(), this);
        recyclerView.setAdapter(adapter);

//        int a = QuestionData.size();
//        test.setText(String.valueOf(a));
//        AnswerData.Answer_initial(a);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AnswerItem> data = new ArrayList<>();
                data = AnswerData.getListData();
                jsonArray = new JSONArray();
                JSONObject tmpObj = null;
                int count = data.size();
                for(int i = 0; i < count; i++)
                {
                    tmpObj = new JSONObject();
                    try {
                        tmpObj.put("from" , data.get(i).getQuestion_from());
                        tmpObj.put("to", data.get(i).getQuestion_to());
                        tmpObj.put("score", data.get(i).getScore());
                        tmpObj.put("group",GroupData.getCurrent_group());
                        jsonArray.put(tmpObj);
                        tmpObj = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                new PostAnswer().execute();
                test.setText(String.valueOf(jsonArray.toString()));
            }
        });
    }



    class getQuestion extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("current_user",GroupData.get_current_user()));
            nameValuePairs.add(new BasicNameValuePair("gid",GroupData.getCurrent_group()));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/get_evaluation_list.php",
                    "POST", nameValuePairs);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                // products found
                // Getting Array of patients
                groups = json.getJSONArray("groups");
                // looping through All Patients
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable(){
                @Override
                public void run(){

                    length = groups.length();
                    QuestionData.clear();

                    for (int i = 0; i <length; i++) {
                        try {
                            JSONObject obj = groups.getJSONObject(i);


                            String name = null;

                            String user_name = obj.getString("name");
                            QuestionData.adddata(user_name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                }
            });


            return null;



        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TaskHome.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            pDialog.dismiss();
            QuestionData.getListData();
            adapter.notifyDataSetChanged();
            int a = QuestionData.size();
            AnswerData.Answer_initial(a);
        }
    }


    class PostAnswer extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("answer",jsonArray.toString()));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/test.php",
                    "POST", nameValuePairs);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                // products found
                // Getting Array of patients
                // looping through All Patients
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TaskHome.this);
            pDialog.setMessage("Updating data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            pDialog.dismiss();
        }
    }



}
