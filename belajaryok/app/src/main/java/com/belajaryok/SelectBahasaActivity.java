package com.belajaryok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belajaryok.adapter.BahasaAdapter;
import com.belajaryok.model.Bahasa;

import java.util.ArrayList;

public class SelectBahasaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Bahasa> arrayList;
    private SharedPreferences shared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bahasa_activity);

        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(shared != null) {
            if(!shared.getString("username", "").isEmpty() && !shared.getString("bahasa","").isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), ViewFragment.class);
                startActivity(intent);
                finish();
            }
        }


        recyclerView = findViewById(R.id.recycler_view);
        arrayList = new ArrayList<Bahasa>();
        arrayList.add(new Bahasa("id", "Indonesia", R.drawable.flag_indonesia));
        arrayList.add(new Bahasa("en", "English", R.drawable.flag_english));

        BahasaAdapter bahasaAdapter = new BahasaAdapter(this, arrayList, new BahasaAdapter.OnClickListener() {
            @Override
            public void OnClick(Bahasa bahasa, int i) {
                SharedPreferences.Editor edit = shared.edit();
                edit.putString("bahasa", bahasa.bahasa);
                edit.apply();
                Intent intent = new Intent(getApplicationContext(), ViewFragment.class);
                startActivity(intent);
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bahasaAdapter);


    }
}
