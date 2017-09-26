package com.jiangxin.peerevaluation2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.adapter.Question_Adapter;
import com.jiangxin.peerevaluation2.model.AnswerItem;
import com.jiangxin.peerevaluation2.model.GroupData;
import com.jiangxin.peerevaluation2.model.GroupItem;
import com.jiangxin.peerevaluation2.model.QuestionData;
import com.jiangxin.peerevaluation2.model.QuestionItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TaskHome extends AppCompatActivity {

    String position;
    int item_position;
    Button submit;
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
        Intent intent = getIntent();
        //to initial the test
        questionData = new QuestionData();
        questionData.initial();

        //test
        position = intent.getStringExtra("position");
        item_position = Integer.parseInt(position);
        GroupItem groupItem = GroupData.getListData(item_position);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Question_Adapter(QuestionData.getListData(), this);
        recyclerView.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<QuestionItem> data = new ArrayList<>();
                data = QuestionData.getListData();
                JSONArray jsonArray = new JSONArray();
                JSONObject tmpObj = null;
                int count = data.size();
                for(int i = 0; i < count; i++)
                {
                    tmpObj = new JSONObject();
                    try {
                        tmpObj.put("question_from" , data.get(i).getName());
                        tmpObj.put("question_to", data.get(i).getId());
                        tmpObj.put("question_score", data.get(i).getType());
                        jsonArray.put(tmpObj);
                        tmpObj = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                test.setText(String.valueOf(jsonArray.toString()));
            }
        });
    }
}
