package com.example.raul.combuyappv20.data.Remota;

import com.example.raul.combuyappv20.data.Local.Item;
import com.example.raul.combuyappv20.data.Local.Local;
import com.example.raul.combuyappv20.data.Local.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CombuyClient {
    @GET("localnegocio/")
    Call<List<Local>> getListLocales();
    @GET("buscarproducto/{producto}")
    Call<List<Local>> getLocalesProducto(@Path("producto") String producto);

    @GET("buscarnegocio/{negocio}")
    Call<List<Local>> getLocales(@Path("negocio") String negocio);


    @GET("item/{nomproducto}")
    Call<List<Item>> getListItems(@Path("nomproducto") String nomproducto);
    @GET("listitem/")
    Call<List<Item>> getTotalItems();

/*
    @GET("login/{user}/{pass}")
    Call<User> getUser(@Path("user") String user, @Path("pass") String pass);
    @POST("user/register/")
    Call<User> createUser(@Body User user);
    @PUT("user/update/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);
    @GET("/valid/{username}")
    Call<String> isValid(@Path("username") String username);
*/
}
