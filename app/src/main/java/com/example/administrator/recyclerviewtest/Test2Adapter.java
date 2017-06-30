package com.example.administrator.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Test2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> dates;
    private Context context;    // 上下文Context

    public Test2Adapter(List<String> dates, Context context){
        this.dates = dates;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test2layout, null));
        return holder;
    }

//    public List<String> getDates() {
//        return dates;
//    }
//
//    public void setDates(List<String> dates) {
//        this.dates = dates;
//        notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String data = dates.get(position);
        ((ViewHolder) holder).command.setText(data);
        ((ViewHolder) holder).command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dates.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dates.size()-position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView command;
        public ViewHolder(View view) {
            super(view);
            command = (TextView) view.findViewById(R.id.command);
        }
    }
}
