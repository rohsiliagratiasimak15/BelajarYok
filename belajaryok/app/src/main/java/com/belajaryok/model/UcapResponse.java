package com.belajaryok.model;


import java.util.List;

public class UcapResponse {
    public boolean status;
    public String message;
    public List<Ucap> listUcap;

    public List<Ucap> getUcap() {
        return listUcap;
    }
}
