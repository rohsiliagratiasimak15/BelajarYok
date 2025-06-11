package com.belajaryok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.belajaryok.api.RetrofitClient;
import com.belajaryok.model.Game;
import com.belajaryok.model.GameResponse;
import com.belajaryok.services.GameService;
import com.belajaryok.sql.SqlHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayGameActivity extends AppCompatActivity {

    private TextView text_pertanyaan, text_jawaban, text_hati;
    private ProgressBar progressBar;
    private AppCompatButton btn_jawaban_1, btn_jawaban_2, btn_jawaban_3, btn_jawaban_4, btn_jawaban_5, btn_jawaban_6;
    private Button btn_periksa_jawaban;

    private List<Game> daftarGame = new ArrayList<>();
    private String currentJawaban = "";
    private int progressValue = 0;
    private int jumlahHati = 5;
    private SharedPreferences shared;
    private SqlHelper sqlHelper;
    private int level = 0;
    private RelativeLayout bg;
    private View viewDarkLight1, viewDarkLight2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playgameactivity);

        bg = (RelativeLayout)findViewById(R.id.bg);

        shared = getSharedPreferences("user", MODE_PRIVATE);

        sqlHelper = new SqlHelper(this);

        jumlahHati = Integer.parseInt(sqlHelper.getTableHeart(shared.getString("username","").toString()));
        level = Integer.parseInt(sqlHelper.getTableLevel(shared.getString("username","").toString()));

        text_pertanyaan = findViewById(R.id.text_soal);
        text_jawaban = findViewById(R.id.text_isi_jawaban);
        text_hati = findViewById(R.id.text_hati);
        text_hati.setText(jumlahHati+"");
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);



        btn_jawaban_1 = findViewById(R.id.appButtonJawab1);
        btn_jawaban_2 = findViewById(R.id.appButtonJawab2);
        btn_jawaban_3 = findViewById(R.id.appButtonJawab3);
        btn_jawaban_4 = findViewById(R.id.appButtonJawab4);
        btn_jawaban_5 = findViewById(R.id.appButtonJawab5);
        btn_jawaban_6 = findViewById(R.id.appButtonJawab6);
        btn_periksa_jawaban = findViewById(R.id.btnPeriksaJawaban);

        btn_periksa_jawaban.setOnClickListener(v -> {
            String jawabanUser = text_jawaban.getText().toString().trim();
            if(jumlahHati < 0) {
                Toast.makeText(PlayGameActivity.this, "Yah, kamu game over", Toast.LENGTH_SHORT).show();
            }else{
                if (jawabanUser.equalsIgnoreCase(currentJawaban.trim())) {
                    ambilData(); // Ini sudah memanggil resetJawabanButtons()
                    Toast.makeText(getApplicationContext(), "Jawaban benar!", Toast.LENGTH_SHORT).show();
                    progressValue += 20;
                    sqlHelper.updatePoint(shared.getString("username", ""), daftarGame.get(0).getPoint());
                } else {
                    ambilData(); // Ini sudah memanggil resetJawabanButtons()
                    Toast.makeText(getApplicationContext(), "Jawaban salah! Benar: " + currentJawaban, Toast.LENGTH_LONG).show();
                    jumlahHati--;
                    sqlHelper.setTableHeart(shared.getString("username", ""), jumlahHati);
                }
                text_hati.setText(String.valueOf(jumlahHati));
                progressBar.setProgress(progressValue);
            }
            if(progressValue == 100) {
                level += 1;
                sqlHelper.setTableLevel(shared.getString("username", ""), level);
                Toast.makeText(PlayGameActivity.this, "Next Level", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ViewFragment.class);
                startActivity(intent);
                finish();
            }
        });



        View.OnClickListener jawabanListener = view -> {
            AppCompatButton btn = (AppCompatButton) view;
            String kata = btn.getText().toString();
            String currentText = text_jawaban.getText().toString();
            text_jawaban.setText(currentText.isEmpty() ? kata : currentText + " " + kata);

            // Nonaktifkan tombol setelah diklik
            btn.setEnabled(false);
            btn.setAlpha(0.5f); // Membuat tombol terlihat lebih redup
        };

        btn_jawaban_1.setOnClickListener(jawabanListener);
        btn_jawaban_2.setOnClickListener(jawabanListener);
        btn_jawaban_3.setOnClickListener(jawabanListener);
        btn_jawaban_4.setOnClickListener(jawabanListener);
        btn_jawaban_5.setOnClickListener(jawabanListener);
        btn_jawaban_6.setOnClickListener(jawabanListener);

        viewDarkLight1 = findViewById(R.id.viewDarkLight1);
        viewDarkLight2 = findViewById(R.id.viewDarkLight2);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(shared != null) {
                    String color = shared.getString("color", "light");
                    if(color.equals("light")) {
                        bg.setBackgroundColor(getColor(R.color.white));
                        viewDarkLight1.setBackgroundColor(getColor(R.color.black));
                        viewDarkLight2.setBackgroundColor(getColor(R.color.black));
                        btn_jawaban_1.setBackgroundResource(R.drawable.btn_click);
                        btn_jawaban_2.setBackgroundResource(R.drawable.btn_click);
                        btn_jawaban_3.setBackgroundResource(R.drawable.btn_click);
                        btn_jawaban_4.setBackgroundResource(R.drawable.btn_click);
                        btn_jawaban_5.setBackgroundResource(R.drawable.btn_click);
                        btn_jawaban_6.setBackgroundResource(R.drawable.btn_click);

                        btn_jawaban_1.setTextColor(Color.BLACK);
                        btn_jawaban_2.setTextColor(Color.BLACK);
                        btn_jawaban_3.setTextColor(Color.BLACK);
                        btn_jawaban_4.setTextColor(Color.BLACK);
                        btn_jawaban_5.setTextColor(Color.BLACK);
                        btn_jawaban_6.setTextColor(Color.BLACK);

                        text_pertanyaan.setTextColor(getColor(R.color.black));
                        text_jawaban.setTextColor(getColor(R.color.black));
                        text_hati.setTextColor(getColor(R.color.black));
                    }else{
                        bg.setBackgroundColor(getColor(R.color.black));
                        viewDarkLight1.setBackgroundColor(getColor(R.color.white));
                        viewDarkLight2.setBackgroundColor(getColor(R.color.white));
                        btn_jawaban_1.setBackgroundResource(R.drawable.btn_click_light);
                        btn_jawaban_2.setBackgroundResource(R.drawable.btn_click_light);
                        btn_jawaban_3.setBackgroundResource(R.drawable.btn_click_light);
                        btn_jawaban_4.setBackgroundResource(R.drawable.btn_click_light);
                        btn_jawaban_5.setBackgroundResource(R.drawable.btn_click_light);
                        btn_jawaban_6.setBackgroundResource(R.drawable.btn_click_light);

                        btn_jawaban_1.setTextColor(Color.WHITE);
                        btn_jawaban_2.setTextColor(Color.WHITE);
                        btn_jawaban_3.setTextColor(Color.WHITE);
                        btn_jawaban_4.setTextColor(Color.WHITE);
                        btn_jawaban_5.setTextColor(Color.WHITE);
                        btn_jawaban_6.setTextColor(Color.WHITE);

                        text_pertanyaan.setTextColor(getColor(R.color.white));
                        text_jawaban.setTextColor(getColor(R.color.white));
                        text_hati.setTextColor(getColor(R.color.white));
                    }
                }
            }
        });

        ambilData();
    }

    private void ambilData() {
        resetJawabanButtons(); // Tambahkan di sini

        GameService apiService = RetrofitClient.getClient().create(GameService.class);
        Call<GameResponse> call = apiService.getGames();
        call.enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    GameResponse gameResponse = response.body();
                    if(gameResponse.status) {
                        daftarGame.clear();
                        daftarGame.addAll(gameResponse.listGames);
                        tampilkanSoalAcak();
                    } else {
                        Toast.makeText(PlayGameActivity.this, "Tidak ada game", Toast.LENGTH_SHORT).show();
                    }



                }
            }

            @Override
            public void onFailure(Call<GameResponse> call, Throwable t) {
                Toast.makeText(PlayGameActivity.this, "Error jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkanSoalAcak() {
        if (daftarGame == null || daftarGame.isEmpty()) return;

        // Ambil soal acak
        Game game = daftarGame.get(new Random().nextInt(daftarGame.size()));
        if (game.soal == null || game.jawaban == null || game.jawaban.isEmpty()) return;

        // Gabungkan kata-kata soal
        String soalGabungan = String.join(" ", game.soal);
        text_pertanyaan.setText(soalGabungan);

        // Kosongkan jawaban user
        text_jawaban.setText("");

        // Simpan jawaban benar
        currentJawaban = game.jawabanBenar;

        // Salin jawaban dan isi dengan kata acak jika kurang dari 6
        List<String> kataJawaban = new ArrayList<>(game.jawaban);
        while (kataJawaban.size() < 6) {
            String kataRandom = ambilKataRandom();
            if (!kataJawaban.contains(kataRandom)) {
                kataJawaban.add(kataRandom);
            }
        }

        // Potong jika lebih dari 6
        if (kataJawaban.size() > 6) {
            kataJawaban = kataJawaban.subList(0, 6);
        }

        // Acak posisi kata
        Collections.shuffle(kataJawaban);

        // Atur tombol dan background
        List<Button> tombolJawaban = Arrays.asList(
                btn_jawaban_1, btn_jawaban_2, btn_jawaban_3,
                btn_jawaban_4, btn_jawaban_5, btn_jawaban_6
        );

        for (int i = 0; i < tombolJawaban.size(); i++) {
            Button btn = tombolJawaban.get(i);
            btn.setText(kataJawaban.get(i));
            btn.setSelected(false);
        }
    }


    private String ambilKataRandom() {
        if (daftarGame == null || daftarGame.isEmpty()) return "kata";
        Game game = daftarGame.get(new Random().nextInt(daftarGame.size()));
        String jawabanAcak = game.jawaban.get(new Random().nextInt(game.jawaban.size()));
        String[] kata = jawabanAcak.split(" ");
        return kata[new Random().nextInt(kata.length)];
    }

    private void resetJawabanButtons() {
        btn_jawaban_1.setEnabled(true);
        btn_jawaban_2.setEnabled(true);
        btn_jawaban_3.setEnabled(true);
        btn_jawaban_4.setEnabled(true);
        btn_jawaban_5.setEnabled(true);
        btn_jawaban_6.setEnabled(true);

        btn_jawaban_1.setAlpha(1.0f);
        btn_jawaban_2.setAlpha(1.0f);
        btn_jawaban_3.setAlpha(1.0f);
        btn_jawaban_4.setAlpha(1.0f);
        btn_jawaban_5.setAlpha(1.0f);
        btn_jawaban_6.setAlpha(1.0f);

        // Reset text jawaban juga
        text_jawaban.setText("");
    }
}
