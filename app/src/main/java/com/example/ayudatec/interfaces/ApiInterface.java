package com.example.ayudatec.interfaces;

import android.media.Image;
import com.example.ayudatec.Alumno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("api/alumnos")
    Call<Alumno> getAlumnoInformation(@Body Alumno alumno);
}
