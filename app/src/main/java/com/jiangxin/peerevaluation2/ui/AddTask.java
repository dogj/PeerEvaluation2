package com.jiangxin.peerevaluation2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.adapter.Adapter;
import com.jiangxin.peerevaluation2.model.Data;

/**
 * Created by Administrator on 2017/1/12.
 */

public class AddTask extends AppCompatActivity {

    Button add_new;
    Button refresh;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_item);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_new= (Button) findViewById(R.id.add_new_task);
        refresh= (Button) findViewById(R.id.refresh_task);
        data= new Data();
        data.initial();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.adddata();
            }
        });

        Adapter adapter = new Adapter(Data.getListData(), this);
//        adapter.setOnItemClickListener(new Adapter2.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, String data) {
//                Toast.makeText(AddTask.this,data,Toast.LENGTH_SHORT).show();
//            }
//        });
        recyclerView.setAdapter(adapter);
    }


    public void refresh() {
        startActivity(new Intent(AddTask.this,AddTask.class));
    }


}
