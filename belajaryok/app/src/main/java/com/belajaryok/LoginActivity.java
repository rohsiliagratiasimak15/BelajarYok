package com.belajaryok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.belajaryok.sql.SqlHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText input_name;
    private Button btnSubmit;
    private SharedPreferences shared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(shared != null) {
            if(!shared.getString("username", "").isEmpty() && !shared.getString("bahasa","").isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), SelectBahasaActivity.class);
                startActivity(intent);
                finish();
            }
        }
        
        
        input_name = findViewById(R.id.input_name);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v-> simpan());
        

    }

    private void simpan() {
        if(input_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nama belum di isi", Toast.LENGTH_SHORT).show();
        }else{
            SqlHelper sqlHelper = new SqlHelper(this);
            boolean insert = sqlHelper.insertUser(input_name.getText().toString());
            if(insert) {
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("username", input_name.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), SelectBahasaActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Gagal simpan data", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
