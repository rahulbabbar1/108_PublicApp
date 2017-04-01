package com.emergency.a108_publicapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrustContactAdapter extends RecyclerView.Adapter<TrustContactAdapter.MyViewHolder> {

    private ArrayList<TrustContact> contactList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.number);

        }
    }


    public TrustContactAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TrustContact con = contactList.get(position);
        holder.name.setText(con.getName());
        holder.number.setText(con.getNumber());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addToList(TrustContact contact) {
        contactList.add(contact);
        this.notifyDataSetChanged();
    }

    public void removeFromList(int position) {
        contactList.remove(position);
        notifyDataSetChanged();
    }
}
/**
 * Created by admin on 01-04-2017.
 */


