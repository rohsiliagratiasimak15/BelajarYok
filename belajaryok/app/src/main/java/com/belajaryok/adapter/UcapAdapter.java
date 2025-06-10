package com.belajaryok.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belajaryok.R;
import com.belajaryok.model.Ucap;

import java.util.ArrayList;

public class UcapAdapter extends RecyclerView.Adapter<UcapAdapter.ViewHolder> {

    Context context;
    ArrayList<Ucap> arrayList;
    SharedPreferences sharedPreferences;
    OnClickListener onClickListener;

    public UcapAdapter(Context context, ArrayList<Ucap> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
        this.sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener=onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ucap, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ucap data = arrayList.get(position);
        String bahasa = sharedPreferences.getString("bahasa","");
        if(bahasa == "Indonesia") {
            holder.btn.setText(data.indo);
        } else {
            holder.btn.setText(data.english);
        }

        holder.btn.setOnClickListener(v->onClickListener.onClick(data,position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btnUcap);
            btn.setBackground(context.getResources().getDrawable(R.drawable.btn_click));
        }
    }
    public interface OnClickListener {
        void onClick(Ucap ucap, int i);
    }
}
