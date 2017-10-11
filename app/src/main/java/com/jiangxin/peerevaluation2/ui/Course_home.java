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
import android.widget.Toast;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.adapter.Course_Adapter;
import com.jiangxin.peerevaluation2.model.GroupData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import old.JSONParser;

/**
 * Created by Administrator on 2017/1/12.
 */

public class Course_home extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    String message;
    String pid;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PID = "pid";
    JSONArray course_list = null;
    private ProgressDialog pDialog;
    Button add_new;

    int length;
    Button join;
    GroupData groupData;
    RecyclerView recyclerView;
    Course_Adapter adapter;
    JSONArray groups = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_home);
        new getCourse().execute();
        recyclerView = (RecyclerView) findViewById(R.id.rec_task_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_new= (Button) findViewById(R.id.add_group);
        groupData = new GroupData();
        join = (Button) findViewById(R.id.join_course);
        adapter = new Course_Adapter(GroupData.getListData(), this);
        adapter.setOnItemClickListener(new Course_Adapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {

                if(GroupData.isDebug()){
                    Toast.makeText(Course_home.this,data,Toast.LENGTH_SHORT).show();
                }else{

                }

                int item_position = Integer.parseInt(data);
                GroupData.setCurrent_group(GroupData.getCurrent_group(item_position));
                GroupData.setCurrent_group_name(GroupData.getCurrent_group_name(item_position));
                startActivity(new Intent(Course_home.this,TaskHome.class));
            }
        });
        recyclerView.setAdapter(adapter);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Course_home.this,JoinCourse.class));
            }
        });


        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Course_home.this,AddCourse.class));
            }
        });


    }


    public void refresh() {
        new getCourse().execute();
        startActivity(new Intent(Course_home.this,Course_home.class));
    }




    class getCourse extends AsyncTask<String,Void,Long> {


        @Override
        protected Long doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("pid",GroupData.get_current_user()));
            JSONObject json = jsonParser.makeHttpRequest("https://dogj.000webhostapp.com/evaluation/get_group_list.php",
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
                    GroupData.clear();
                    for (int i = 0; i <length; i++) {
                        try {
                        JSONObject obj = groups.getJSONObject(i);
                        String name = null;
                            name = obj.getString("group_name");
                        String group_reason = obj.getString("group_reason");
                        String group_code = obj.getString("group_code");
                        GroupData.adddata(name,group_reason,group_code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                    adapter.notifyDataSetChanged();

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
            pDialog = new ProgressDialog(Course_home.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            pDialog.dismiss();
            GroupData.getListData();
            adapter.notifyItemInserted(0);
            adapter.notifyDataSetChanged();

        }
    }


}
