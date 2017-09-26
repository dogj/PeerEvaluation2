package com.jiangxin.peerevaluation2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * Created by Administrator on 2017/1/12.
 */

public class Course_home extends AppCompatActivity {

    Button add_new;

    Button join;
    GroupData groupData;
    RecyclerView recyclerView;
    Course_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_home);
        recyclerView = (RecyclerView) findViewById(R.id.rec_task_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_new= (Button) findViewById(R.id.add_group);
        groupData = new GroupData();
        join = (Button) findViewById(R.id.join_course);

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

        adapter = new Course_Adapter(GroupData.getListData(), this);
        adapter.setOnItemClickListener(new Course_Adapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {


                Toast.makeText(Course_home.this,data,Toast.LENGTH_SHORT).show();
                int item_position = Integer.parseInt(data);
                SharedPreferences.Editor sharedata = getSharedPreferences("groupData", 0).edit();
                sharedata.putString("position",data);
                sharedata.commit();



                Intent intent = new Intent(Course_home.this,TaskHome.class);
                intent.putExtra("position",data);
                startActivity(intent);
            }
        });
//        adapter.setOnItemClickListener(new Adapter2.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, String groupData) {
//                Toast.makeText(AddTask.this,groupData,Toast.LENGTH_SHORT).show();
//            }
//        });
        recyclerView.setAdapter(adapter);
    }


    public void refresh() {

        startActivity(new Intent(Course_home.this,Course_home.class));
    }


}
