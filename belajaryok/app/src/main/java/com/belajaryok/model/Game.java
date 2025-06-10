package com.belajaryok.model;
import java.util.List;

public class Game {
    public List<String> soal;
    public List<String> jawaban;
    public String jawabanBenar; // "Kopi Dengan Gula"
    public int point = 0;

    public int getPoint() {
        return point;
    }
}