package com.belajaryok.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belajaryok.R;
import com.belajaryok.adapter.UcapAdapter;
import com.belajaryok.api.RetrofitClient;
import com.belajaryok.model.Ucap;
import com.belajaryok.model.UcapResponse;
import com.belajaryok.services.GameService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UcapFragment extends Fragment {
    private View view;
    private RecyclerView recycler_view;
    private ArrayList<Ucap> array;
    private SharedPreferences shared;
    private TextToSpeech textToSpeech;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fg_bahasa, container, false);
        shared = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        ImageView rl_bg_game = view.findViewById(R.id.imageView);
        Glide.with(getContext())
                .load(R.drawable.bg_game)
                .into(rl_bg_game);
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    if(shared.getString("bahasa","") == "Indonesia"){
                        textToSpeech.setLanguage(Locale.forLanguageTag("id-ID")); // Atau Locale("id", "ID") untuk Bahasa Indonesia
                    }else{
                        textToSpeech.setLanguage(Locale.US); // Atau Locale("id", "ID") untuk Bahasa Indonesia
                    }
                }
            }
        });

        recycler_view = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recycler_view.setLayoutManager(gridLayoutManager);
        array = new ArrayList<Ucap>();
        final UcapAdapter adapter = new UcapAdapter(getContext(), array);
        recycler_view.setAdapter(adapter);
        GameService retrofit = RetrofitClient.getClient().create(GameService.class);
        Call<UcapResponse> ucapCall = retrofit.getUcap();
        ucapCall.enqueue(new Callback<UcapResponse>() {
            @Override
            public void onResponse(Call<UcapResponse> call, Response<UcapResponse> response) {
                array.clear();
                if(response.isSuccessful() && response.body() != null) {
                    UcapResponse res = response.body();
                    for(Ucap ucap : res.listUcap) {
                        array.add(ucap);
                    }
                    adapter.setOnClickListener(new UcapAdapter.OnClickListener() {
                        @Override
                        public void onClick(Ucap ucap, int i) {
                            if(shared.getString("bahasa","") == "Indonesia"){
                                speak(ucap.indo);
                            }else{
                                speak(ucap.english);
                            }
                        }
                    });
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UcapResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Tidak ada koneksi", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
    private void speak(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

}
