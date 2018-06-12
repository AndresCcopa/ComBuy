package com.example.raul.combuyappv20.data.Remota;

import android.util.Log;

import com.example.raul.combuyappv20.data.Local.Local;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LocalRetrofit {

    CombuyClient api;
    List<Local> listLocal;
    public LocalRetrofit() {

        api= new Api().getRetrofit();
        listLocal = null;

    }

    public List<Local> getListLocal(){
        try {
            final Call<List<Local>> callsync= api.getListLocales();
            Response<List<Local>> response=callsync.execute();
            listLocal=response.body();
            if(response.code()==200){
                Log.v("RETRO","Response Satisfed!");
            }else{
                Log.v("RETRO","Failed U.U");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listLocal;
    }
    public List<Local> getLocalesProducto(String producto){
        try {
            final Call<List<Local>> callsync= api.getLocalesProducto(producto);
            Response<List<Local>> response=callsync.execute();
            listLocal=response.body();
            if(response.code()==200){
                Log.v("RETRO","Response Satisfed!");
            }else{
                Log.v("RETRO","Failed U.U");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLocal;
    }
}
