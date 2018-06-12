package com.example.raul.combuyappv20.utils;

import android.location.Location;

import com.example.raul.combuyappv20.data.Local.Local;

import java.util.ArrayList;
import java.util.List;

public class CombuyUtils {

    public static List<Local> obtenerCercanos(List<Local> lista, Location ubicacionActual, int nlocales){

        int count=0;
        Location aux= new Location("");
        List<Local> retorno = new ArrayList<Local>();

        for(Local i: lista){
            aux.setLatitude(i.getLatitud());
            aux.setLongitude(i.getLongitud());
            i.setDistancia(ubicacionActual.distanceTo(aux));
            aux.reset();
        }
        while(nlocales>count){
            retorno.add(obtenerMasCercano(lista));
            lista.remove(obtenerMasCercano(lista));
            count++;
        }
        return retorno;
    }

    public static Local obtenerMasCercano(List<Local> locales){
        Local localCercano= locales.get(0);
        for(Local p: locales){
            if(p.getDistancia()<localCercano.getDistancia()){
                localCercano=p;
            }
        }
        return localCercano;
    }

}
