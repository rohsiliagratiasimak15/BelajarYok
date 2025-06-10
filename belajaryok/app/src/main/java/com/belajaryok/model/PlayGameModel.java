package com.belajaryok.model;

import java.util.List;

public class PlayGameModel {
    private boolean status;
    private String message;
    private List<ListGame> listGames;

    // Getter dan Setter
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListGame> getListGames() {
        return listGames;
    }
    public void setListGames(List<ListGame> listGames) {
        this.listGames = listGames;
    }

    // Inner class harus public supaya Retrofit bisa mapping JSON ke objek ini
    public static class ListGame {
        private String[] soal;
        private String[] jawaban;
        private int point;

        public String[] getSoal() {
            return soal;
        }
        public void setSoal(String[] soal) {
            this.soal = soal;
        }

        public String[] getJawaban() {
            return jawaban;
        }
        public void setJawaban(String[] jawaban) {
            this.jawaban = jawaban;
        }

        public int getPoint() {
            return point;
        }
        public void setPoint(int point) {
            this.point = point;
        }
    }
}
