package com.jiangxin.peerevaluation2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.adapter.Adapter;
import com.jiangxin.peerevaluation2.model.Data;

/**
 * Created by Administrator on 2017/1/10.
 */

public class double_item2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_list2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Adapter adapter = new Adapter(Data.getListData(), this);
        recyclerView.setAdapter(adapter);
    }




}