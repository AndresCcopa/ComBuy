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
    public Call<List<Local>> getListLocales();
    @GET("buscarproducto/{producto}")
    public Call<List<Local>> getLocalesProducto(@Path("producto") String producto);


    @GET("login/{user}/{pass}")
    public Call<User> getUser(@Path("user") String user, @Path("pass") String pass);
    @POST("user/register/")
    public Call<User> createUser(@Body User user);
    @PUT("user/update/{id}")
    public Call<User> updateUser(@Path("id") int id, @Body User user);
    @GET("/valid/{username}")
    public Call<String> isValid(@Path("username") String username);


    @GET("item/{nomproducto}")
    public Call<List<Item>> getListItems(@Path("nomproducto") String nomproducto);
    @GET("listitem/")
    public Call<List<Item>> getTotalItems();

}
