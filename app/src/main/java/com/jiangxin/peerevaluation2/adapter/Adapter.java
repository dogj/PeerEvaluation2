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

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> listData;
    private LayoutInflater inflater;



    public Adapter (List<ListItem> listData, Context c){

        this.inflater= LayoutInflater.from(c);
        this.listData= listData;

    }


        public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous

            return listData.get(position).getType();
    }

@Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = inflater.inflate(R.layout.list_name,parent,false);
                return new MyViewHolder0(view);
            case 2:
                View view2 = inflater.inflate(R.layout.list_item2,parent,false);
                return new MyViewHolder2(view2);
        }
    return null;
}


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:

                MyViewHolder0 holder0 =(MyViewHolder0)holder;
                ListItem item =listData.get(position);
                holder0.title.setText(item.getTitles());
                holder0.icon.setImageResource(item.getImageResId());
                break;

            case 2:
                MyViewHolder2 holder2 =(MyViewHolder2)holder;
                ListItem item2 =listData.get(position);
                holder2.title.setText(item2.getTitles());
                break;
        }
    }





    @Override
    public int getItemCount() {
        return listData.size();
    }

    private class MyViewHolder0 extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private View container;

        private MyViewHolder0(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView) itemView.findViewById(R.id.im_item_icon);
            container = itemView.findViewById(R.id.cont_item_root);

        }
    }

    private class MyViewHolder2 extends RecyclerView.ViewHolder {
        private TextView title;


        private MyViewHolder2(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_item2_text);
        }
    }
}
