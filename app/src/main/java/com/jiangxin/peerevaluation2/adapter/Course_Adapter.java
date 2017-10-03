package com.jiangxin.peerevaluation2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.model.GroupItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class Course_Adapter extends RecyclerView.Adapter<Course_Adapter.MyViewHolder> implements View.OnClickListener {


    private List<GroupItem> listData;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public Course_Adapter(List<GroupItem> listData, Context c){

        this.inflater= LayoutInflater.from(c);
        this.listData= listData;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_course,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GroupItem item =listData.get(position);
        holder.itemView.setTag(position);
        holder.course_name.setText(item.getName());
        holder.course_code.setText(item.getCode() );
        holder.textView_course_id.setText(item.getId());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView course_name;
        public TextView course_code;
        public TextView textView_course_id;
        public ImageView course_delete;
        private View container;

        public MyViewHolder(View itemView) {
            super(itemView);
            course_name=(TextView)itemView.findViewById(R.id.course_name);
            course_code=(TextView)itemView.findViewById(R.id.course_code_join);
            textView_course_id=(TextView)itemView.findViewById(R.id.course_id);
            course_delete= (ImageView) itemView.findViewById(R.id.course_delete);
            container=itemView.findViewById(R.id.cont_course_item);
            course_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));

            //注意这里使用getTag方法获取数据

//            listData.remove(Integer.parseInt(String.valueOf(v.getTag())));
//            notifyItemRangeRemoved(Integer.parseInt(String.valueOf(v.getTag())),listData.size());
//           notifyItemRemoved(Integer.parseInt(String.valueOf(v.getTag())));
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRangeRemoved(position,listData.size());
        notifyItemRemoved(position);


    }
}