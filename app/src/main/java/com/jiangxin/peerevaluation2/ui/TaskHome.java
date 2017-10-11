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
import com.jiangxin.peerevaluation2.model.QuestionItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import old.JSONParser;

public class TaskHome extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();

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
    int answered_count;

    int limit;
    String outcome;
    boolean finshed = true;
    Button submit;
    Button cancel;
    Question_Adapter adapter;
    Button verify;
    TextView group_hint;
    TextView test;
    TextView email;
    String reply;
    private static List<QuestionItem> data;
    private boolean clicked = false;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_home);

        recyclerView = (RecyclerView) findViewById(R.id.rec_task_home);
        submit = (Button) findViewById(R.id.Question_submit);
        test= (TextView) findViewById(R.id.question_test);
        test.setText("Current group:  "+GroupData.getCurrent_group_name());
        verify= (Button) findViewById(R.id.verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new group_verify().execute();

            }
        });

        group_hint= (TextView) findViewById(R.id.group_hint);
        email = (TextView) findViewById(R.id.email_send);
        cancel= (Button) findViewById(R.id.task_home_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GroupData.getCurrent_statue()==0){
                startActivity(new Intent(TaskHome.this,Course_home.class));
                }else{
                    startActivity(new Intent(TaskHome.this,Main2Activity.class));
                }

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
                if(GroupData.getCurrent_statue()==0){
                     limit =3;
                }else{
                     limit =5;
                }


                if(AnswerData.getAverage()>limit){
                    Toast.makeText(getApplicationContext(),"current average score is "+AnswerData.getAverage()+" Please make sure it is lower than "+limit,Toast.LENGTH_SHORT).show();
                }else if (!AnswerData.isClicked()){
                    Toast.makeText(getApplicationContext(),"you haven't slected at least one of your group member's score, please select it! ",Toast.LENGTH_SHORT).show();
                } else {
                    List<AnswerItem> data = new ArrayList<>();
                    data = AnswerData.getListData();
                    jsonArray = new JSONArray();
                    JSONObject tmpObj = null;
                    int count = data.size();
                    for (int i = 0; i < count; i++) {
                        tmpObj = new JSONObject();
                        try {
                            tmpObj.put("from", GroupData.getCurrent_user_name());
                            tmpObj.put("to", data.get(i).getQuestion_to());
                            tmpObj.put("score", data.get(i).getScore());
                            tmpObj.put("group", GroupData.getCurrent_group());
                            jsonArray.put(tmpObj);
                            tmpObj = null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    new PostAnswer().execute();
                    if(GroupData.isDebug()){
                        test.setText(String.valueOf(jsonArray.toString()));
                    }else{

                    }
                }
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
                groups = json.getJSONArray("groups");
                // looping through
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

                    if(GroupData.isDebug()){
                        Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                    }else{

                    }

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
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/update_answer.php",
                    "POST", nameValuePairs);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                reply = json.getString(TAG_MESSAGE);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable(){
                @Override
                public void run(){

                    if(GroupData.isDebug()){
                        Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                        test.setText(jsonArray.toString());
                    }else{
                        Toast.makeText(getApplicationContext(),reply,Toast.LENGTH_SHORT).show();
                    }

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



    class group_verify extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<QuestionItem> data2 = new ArrayList<>();
            data2 = QuestionData.getListData();
            jsonArray = new JSONArray();
            JSONObject tmpObj = null;
            int count = data2.size();
            for (int i = 0; i < count; i++) {
                tmpObj = new JSONObject();
                try {
                    tmpObj.put("name", QuestionData.getName(i));
                    jsonArray.put(tmpObj);
                    tmpObj = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("group_member", jsonArray.toString()));
                nameValuePairs.add(new BasicNameValuePair("gid", GroupData.getCurrent_group()));
                JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/verify.php",
                        "POST", nameValuePairs);

                try {
                    outcome= "";

                    Iterator<String> iterator = json.keys();
                    answered_count = 0;
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        int number = Integer.parseInt(json.getString(key));
                        if(number<QuestionData.size()){
                            String member = "member.";
                            if(number>1){
                                member = "members.";
                            }
                            outcome= outcome+key+" has only  evaluated "+number+" "+member+"\n";
                            finshed = false;
                            answered_count++;
                        }
                    }


                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);
                    // looping through
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finshed){
                            email.setText("submit the result to tutor");
                            Toast.makeText(getApplicationContext(), "All members inside the group have already finished their evaluation," +
                                    " your evaluation is good to go, please also check whether all the group members are inside the group", Toast.LENGTH_LONG).show();
                         }else{
                            email.setText("remind them");
                            outcome=outcome+"The whole evaluation is not finished.";
                            Toast.makeText(getApplicationContext(), outcome, Toast.LENGTH_LONG).show();
                        }
                    }
                });



            return null;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TaskHome.this);
            pDialog.setMessage("Connecting to server. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            pDialog.dismiss();
            group_hint.setText(QuestionData.size()-answered_count+" / "+QuestionData.size()+" members finished");

        }
    }}







