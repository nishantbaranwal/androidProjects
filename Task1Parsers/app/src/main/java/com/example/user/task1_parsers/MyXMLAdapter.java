package com.example.user.task1_parsers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class MyXMLAdapter extends RecyclerView.Adapter<MyXMLAdapter.MyViewHolder> {
    AllPizza allPizza[];
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
        holder.name.setText(allPizza[position].getName());
        holder.email.setText(allPizza[position].getCost());
        holder.mobno.setText(allPizza[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return allPizza.length;
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
    public MyXMLAdapter(AllPizza[] allPizza) {
        this.allPizza=allPizza;
    }


}
