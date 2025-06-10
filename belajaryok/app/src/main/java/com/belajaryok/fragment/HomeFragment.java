package com.belajaryok.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.belajaryok.PlayGameActivity;
import com.belajaryok.R;
import com.belajaryok.sql.SqlHelper;
import com.bumptech.glide.Glide;

public class HomeFragment extends Fragment {
    private View view;
    private ImageView imagetoolbar;
    private SqlHelper sqlHelper;
    private SharedPreferences shared;
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        shared = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);


        ImageView btnDarkLight = view.findViewById(R.id.imgDarkLight);
        btnDarkLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = shared.getString("color", "light");
                if(color.equals("light")) {
                    shared.edit().putString("color", "dark").apply();
                    toolbar.setBackgroundColor(getContext().getColor(R.color.black));
                    btnDarkLight.setImageResource(R.drawable.ic_light);
                }else{
                    shared.edit().putString("color", "light").apply();
                    toolbar.setBackgroundColor(getContext().getColor(R.color.white));
                    btnDarkLight.setImageResource(R.drawable.ic_dark);
                }
            }
        });
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("color")) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            String color = shared.getString("color", "light");
                            if (color.equals("light")) {
                                toolbar.setBackgroundColor(getContext().getColor(R.color.black));
                                btnDarkLight.setImageResource(R.drawable.ic_light);
                                Glide.with(getContext())
                                        .load(R.drawable.bg_game)
                                        .into((ImageView) view.findViewById(R.id.img_bg_game));
                            } else {
                                toolbar.setBackgroundColor(getContext().getColor(R.color.white));
                                btnDarkLight.setImageResource(R.drawable.ic_dark);
                                Glide.with(getContext())
                                        .load(R.drawable.dark_bg)
                                        .into((ImageView) view.findViewById(R.id.img_bg_game));
                            }
                        });
                    }
                }
            }

        };



// Jangan lupa daftar di bawah ini (penting!)
        shared.registerOnSharedPreferenceChangeListener(listener);

        sqlHelper = new SqlHelper(getContext());
        
        imagetoolbar = view.findViewById(R.id.imagetoolbar);
        Glide.with(getContext())
                .asGif()
                .load(R.drawable.book)
                .into(imagetoolbar);

        ImageView rl_bg_game = view.findViewById(R.id.img_bg_game);
        Glide.with(getContext())
                .load(R.drawable.bg_game)
                .into(rl_bg_game);


        RelativeLayout tombol1 = view.findViewById(R.id.tombol1);
        tombol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayGameActivity.class);
                startActivity(intent);
            }
        });


        if(shared != null) {
            String color = shared.getString("color", "light");
            if(color.equals("light")) {
                toolbar.setBackgroundColor(getContext().getColor(R.color.black));
                btnDarkLight.setImageResource(R.drawable.ic_light);
                Glide.with(getContext())
                        .load(R.drawable.dark_bg)
                        .into((ImageView) view.findViewById(R.id.img_bg_game));
            } else {
                toolbar.setBackgroundColor(getContext().getColor(R.color.white));
                btnDarkLight.setImageResource(R.drawable.ic_dark);
                Glide.with(getContext())
                        .load(R.drawable.bg_game)
                        .into((ImageView) view.findViewById(R.id.img_bg_game));
            }
        }
        btn1 = view.findViewById(R.id.imgBtn1);
        btn2 = view.findViewById(R.id.imgBtn2);
        btn3 = view.findViewById(R.id.imgBtn3);
        btn4 = view.findViewById(R.id.imgBtn4);
        btn5 = view.findViewById(R.id.imgBtn5);
        btn6 = view.findViewById(R.id.imgBtn6);
        btn7 = view.findViewById(R.id.imgBtn7);
        btn8 = view.findViewById(R.id.imgBtn8);
        btn9 = view.findViewById(R.id.imgBtn9);



        getHeart();


        // Jalankan pengambilan data level dan update UI dengan aman
        new Thread(() -> {
            final String levelStr = sqlHelper.getTableLevel(shared.getString("username", ""));
            final String heartStr = sqlHelper.getTableHeart(shared.getString("username", ""));
            final int point = sqlHelper.getPoint(shared.getString("username", ""));

            // Update UI di thread utama
            requireActivity().runOnUiThread(() -> {
                updateLevelUI(levelStr);
                updateHeartUI(heartStr);
                updatePointUI(point);
            });
        }).start();


        return view;
    }

    private void getPoint() {
        String point = String.valueOf(sqlHelper.getPoint(shared.getString("username", "")));
        TextView text = view.findViewById(R.id.text_coint);
        text.setText(point);
    }

    private void getHeart() {
        String hati = sqlHelper.getTableHeart(shared.getString("username", ""));
        TextView text = view.findViewById(R.id.text_heart);
        text.setText(hati);
    }

    private void updateLevelUI(String levelStr) {
        int level;
        try {
            level = Integer.parseInt(levelStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Level tidak valid!", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageButton[] buttons = {btn1, btn2, btn3, btn4, btn5};

        for (int i = 0; i < level && i < buttons.length; i++) {
            ImageButton btn = buttons[i];
            btn.setBackground(getResources().getDrawable(R.drawable.tombol_selector));
            btn.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), PlayGameActivity.class);
                startActivity(intent);
            });
        }
    }

    private void updateHeartUI(String heartStr) {
        TextView text = view.findViewById(R.id.text_heart);
        text.setText(heartStr);
    }

    private void updatePointUI(int point) {
        TextView text = view.findViewById(R.id.text_coint);
        text.setText(String.valueOf(point));
    }

}
