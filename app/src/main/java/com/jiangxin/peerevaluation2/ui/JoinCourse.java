package com.jiangxin.peerevaluation2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.model.GroupData;
import com.jiangxin.peerevaluation2.model.GroupItem;

public class JoinCourse extends AppCompatActivity {

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
                int code = Integer.parseInt(code_get);
                GroupItem groupItem;
                 groupItem = GroupData.getListData(code);
                Toast.makeText(JoinCourse.this, groupItem.toString(),Toast.LENGTH_SHORT).show();
                if(groupItem.getName()!=""&& groupItem.getId()!=""){
                    textView_course_name_join.setText(groupItem.getName());
                    textView_course_id_join.setText(groupItem.getId());
                }
            }
        });


    }
}
