package com.belajaryok.services;

import com.belajaryok.model.GameResponse;
import com.belajaryok.model.Ucap;
import com.belajaryok.model.UcapResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GameService {
    @GET("games")
    Call<GameResponse> getGames();

    @GET("bahasa")
    Call<UcapResponse> getUcap();
}
