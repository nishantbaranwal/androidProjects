package com.example.user.task1_parsers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    AllContacts allContacts[];
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(allContacts[position].getName());
        holder.email.setText(allContacts[position].getEmailId());
        holder.mobno.setText(allContacts[position].getMobile());
    }

    @Override
    public int getItemCount() {
        return allContacts.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,mobno;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);
            mobno=(TextView)itemView.findViewById(R.id.mobNo);
        }
    }
    public MyAdapter(AllContacts[] allContacts) {
        this.allContacts=allContacts;
    }

    public class XMLViewHolder {
    }
}
