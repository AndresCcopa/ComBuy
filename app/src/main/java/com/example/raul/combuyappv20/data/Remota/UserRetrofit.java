package com.example.raul.combuyappv20.data.Remota;

import android.util.Log;

import com.example.raul.combuyappv20.data.Local.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class UserRetrofit {


    CombuyClient api;
    User currentUser;
    boolean hayUsuario=false;
    public UserRetrofit() {

        api= new Api().getRetrofit();
        currentUser = null;
    }
    public User getUser(String username,String password){
        final Call<User> callsyn=api.getUser(username,password);
        try {
            Response<User> response = callsyn.execute();
            currentUser = response.body();

            if(response.code()!=200){
                Log.v("UserRetrofit","No se pudo obtener informacion del usuario");
                hayUsuario=false;
            }
            else{
                hayUsuario=true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            hayUsuario=false;
        }
        return currentUser;
    }



}
