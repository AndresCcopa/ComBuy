package com.example.raul.combuyappv20.data.Remota;

import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.raul.combuyappv20.data.Local.User;

import java.io.IOException;
import java.util.List;

import static android.app.PendingIntent.getActivity;

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
