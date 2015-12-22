package com.example.jiangwei.myapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jiangwei on 2015/11/21.
 */
public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.ViewHolder> {

    private OnItemClickLisener onItemClickLisener;

    public RecylerAdapter(List<String> mDate) {
        this.mDate = mDate;
    }

    private List<String> mDate;

    public void setOnItemClickLisener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.txt_item, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDate.get(position));
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    //内部类 ViewHolder
    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (onItemClickLisener != null) {
                onItemClickLisener.onItemClick(view, getPosition());
            }
        }
    }

    //定义回调接口
    public interface  OnItemClickLisener {
        void onItemClick(View v, int position);
    }
}
