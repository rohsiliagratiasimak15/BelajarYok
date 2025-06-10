package com.belajaryok;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.belajaryok.fragment.HomeFragment;
import com.belajaryok.fragment.UcapFragment;
import com.nafis.bottomnavigation.NafisBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ViewFragment extends AppCompatActivity {

    private NafisBottomNavigation navifsbottomNavi;
    private static final int ID_HOME = 1;
    private static final int ID_UCAP = 2;
    private FrameLayout frameLayout;
    private MediaPlayer mediaPlayer;
    private SharedPreferences shared;
    private RelativeLayout bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.viewfragment);
        bg = (RelativeLayout)findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shared = getSharedPreferences("user", MODE_PRIVATE);


        frameLayout = findViewById(R.id.frame_layout);
        replaceFragment(ID_HOME);

        navifsbottomNavi = findViewById(R.id.navisfbottomNavi);
        navifsbottomNavi.add(new NafisBottomNavigation.Model(ID_HOME, R.drawable.ic_home));
        navifsbottomNavi.add(new NafisBottomNavigation.Model(ID_UCAP, R.drawable.languages));
        navifsbottomNavi.show(ID_HOME, true);
        navifsbottomNavi.setOnClickMenuListener(model -> {
            if (model.getId() == ID_HOME) {
                replaceFragment(ID_HOME);
            } else if (model.getId() == ID_UCAP) {
                replaceFragment(ID_UCAP);
            }
            return null;
        });



        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("color")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String color = shared.getString("color", "light");
                            if(color.equals("light")) {
                                bg.setBackgroundColor(getColor(R.color.white));
                                navifsbottomNavi.setBackgroundColor(getColor(R.color.white));

                            } else {
                                bg.setBackgroundColor(getColor(R.color.black));
                                navifsbottomNavi.setBackgroundColor(getColor(R.color.black));
                            }
                        }
                    });
                }
            }
        };

// Daftarkan listener-nya, misalnya di onCreate():
        shared.registerOnSharedPreferenceChangeListener(listener);

    }

    private void replaceFragment(int menuId) {
        Fragment fragment = null;

        if (menuId == ID_HOME) {
            fragment = new HomeFragment();
        } else if (menuId == ID_UCAP) {
            fragment = new UcapFragment();
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
    }

    private void backsound(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.backsound);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // resume jika sempat pause
        }
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        backsound(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        backsound(this); // otomatis nyala lagi saat activity dibuka
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
}
