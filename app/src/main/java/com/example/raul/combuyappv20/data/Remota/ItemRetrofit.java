package com.example.raul.combuyappv20.data.Remota;

import android.util.Log;

import com.example.raul.combuyappv20.data.Local.Item;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ItemRetrofit {

    CombuyClient api;
    List<Item> body;
    public ItemRetrofit() {
        api= new Api().getRetrofit();
        body = null;
    }

    public List<Item> getItems(){

        try {
            final Call<List<Item>> callsyn= api.getTotalItems();
            Response<List<Item>> response=callsyn.execute();
            body=response.body();
            if(response.code()==200){
                Log.v("RETRO5","Response Satisfed!");
            }else{
                Log.v("RETRO6","Failed U.U");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
    public List<Item> getListItems(String consulta){

        try {
            final Call<List<Item>> callsyn= api.getListItems(consulta);
            Response<List<Item>> response=callsyn.execute();
            body=response.body();
            if(response.code()==200){
                Log.v("RETRO7","Response Satisfed!");
            }else{
                Log.v("RETRO8","Failed U.U");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
}
