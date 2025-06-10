package com.belajaryok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belajaryok.R;
import com.belajaryok.model.Bahasa;

import java.util.ArrayList;

public class BahasaAdapter extends RecyclerView.Adapter<BahasaAdapter.ViewHolder> {
    Context context;
    ArrayList<Bahasa> arrayList;
    OnClickListener onClickListener;
    public BahasaAdapter(Context context, ArrayList<Bahasa> arrayList, OnClickListener onClickListener) {
        this.context = context;
        this.arrayList= arrayList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bahasa, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bahasa data = arrayList.get(position);
        holder.flagIcon.setImageResource(data.icon);
        holder.bahasa.setText(data.bahasa);

        holder.itemView.setOnClickListener(v-> onClickListener.OnClick(data, position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bahasa;
        ImageView flagIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flagIcon = itemView.findViewById(R.id.imgFlag);
            bahasa = itemView.findViewById(R.id.textBahasa);
        }
    }

    public interface OnClickListener {
        void OnClick(Bahasa bahasa, int i);
    }
}
