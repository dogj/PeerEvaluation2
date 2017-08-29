package com.jiangxin.peerevaluation2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiangxin.peerevaluation2.R;
import com.jiangxin.peerevaluation2.model.ListItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class Adapter2 extends RecyclerView.Adapter<Adapter2.MyViewHolder> implements View.OnClickListener {

    private List<ListItem> listData;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public Adapter2 (List<ListItem> listData, Context c){

        this.inflater= LayoutInflater.from(c);
        this.listData= listData;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_name,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListItem item =listData.get(position);
        String[] a={"哈哈","呵呵","嘻嘻","咩咩"};
        holder.itemView.setTag(a[position]);
        holder.title.setText(item.getTitles());
        holder.icon.setImageResource(item.getImageResId() );
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView icon;
        private View container;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);

            container=itemView.findViewById(R.id.cont_item_root);


        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }
}
