package com.example.a40001811.recyclercardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView1.setText(mDataset[position]);
        holder.mTextView2.setText(mDataset[position]);
        holder.mTextView3.setText(mDataset[position]);
        holder.mTextView4.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;


        public MyViewHolder(View v) {
            super(v);
            mTextView1=(TextView)v.findViewById(R.id.textView1);
            mTextView2=(TextView)v.findViewById(R.id.textView2);
            mTextView3=(TextView)v.findViewById(R.id.textView3);
            mTextView4=(TextView)v.findViewById(R.id.textView4);

        }
    }
}
