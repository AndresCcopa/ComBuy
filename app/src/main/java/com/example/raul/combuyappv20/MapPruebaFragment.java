package com.example.raul.combuyappv20;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.raul.combuyappv20.data.Local.Item;
import com.example.raul.combuyappv20.data.Local.Local;
import com.example.raul.combuyappv20.data.Remota.LocalRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class MapPruebaFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback,OnMapReadyCallback{

    private static final int DEFAULT_ZOOM = 10;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static MapPruebaFragment instance=null;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private GoogleMap mMap;
    private Location CurrentLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean PermisoConcedido ;
    private MapView mapView;

    private static final String ARG_PARAM1 = "param1";

    private LatLng pastPosition = null;
    private LatLng currentPosition = null;
    private String consulta;

    private List<Local> locales=new ArrayList<Local>();
    private List<Local> defLocales;
    private String LOG_TAG="FEIK";

    public MapPruebaFragment() {
        // Required empty public constructor
    }

    public static MapPruebaFragment getInstance(String param1) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        if(instance== null){
            instance = new MapPruebaFragment();
        }
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObtenerPermisodeUbicacion();
        Log.v(LOG_TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            consulta = getArguments().getString(ARG_PARAM1);
        }
        View view = inflater.inflate(R.layout.fragment_map_prueba, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        defLocales = new LocalRetrofit().getListLocal();
    }
    public void updateMap(List<Item> data){

        if(data == null){
            Toast.makeText(getActivity(), "No se encontro el producto U,U", Toast.LENGTH_SHORT).show();
        }else{
            locales.clear();
            for(Item i:data){
                locales.add(i.getIdlocalnegocio());
            }
            agregarCercanos(20);
        }
        Log.v("MAPS-Update","Este es el valor de la variable consulta -> |"+consulta+"|");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
        try{
            if(PermisoConcedido){
                mapView.onResume();//<--
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                //ACA hubo algo v:
                mapView.getMapAsync(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v("MAPS","EN el onmapready");
        mMap = googleMap;
        updateLocationUI();
        ObtenerUbicacion();
    }

    private void agregarLocales(List<Local> lista) {
        Log.v("MAPS","OBTENIENDO LOCALES");
        if(lista!=null){
            mMap.clear();
            for(Local i : lista){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(i.getLatitud(), i.getLongitud()))
                        .title(i.getNombrenegocio())
                        .snippet(i.getDescripcion()));
            }
        }else{
            Toast.makeText(getContext(),"No locales que mostrar u.u", Toast.LENGTH_LONG).show();
        }
    }

    private void ObtenerPermisodeUbicacion() {
        /*
         * Consulta el permison de FINE_LOCATION, el resultado es manejado por el callback
         * onRequestPermissionsResult
         */
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            PermisoConcedido = true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Habilitar GPS")
                        .setMessage("Se requieren permisos de gps")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        PermisoConcedido = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermisoConcedido = true;
                    //mapView.onResume();
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (PermisoConcedido) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                CurrentLocation = null;
                ObtenerPermisodeUbicacion();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void ObtenerUbicacion() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (PermisoConcedido) {
                Task<Location> locationResult = mFusedLocationClient.getLastLocation();

                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            CurrentLocation = task.getResult();
                            currentPosition=new LatLng(CurrentLocation.getLatitude(),CurrentLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    currentPosition, DEFAULT_ZOOM));


                            if(currentPosition != pastPosition){
                                pastPosition = currentPosition;
                                agregarCercanos(20);
                            }

                            Log.v("TASK","ESTA ES LA UBICACION");
                            Log.v("MAPS","Este es el valor de la variable consulta -> |"+consulta+"|");
                            //setValuesMap(consulta,CurrentLocation,5);

                        } else {
                            Log.d("MAPS", "Current location is null. Using defaults.");
                            Log.e("MAPS", "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    public void agregarCercanos(int nlocales){

        int count=0;
        List<Local> lista = defLocales;
        if(!locales.isEmpty()){
            lista = locales;
        }

        Location aux= new Location("");

        List<Local> retorno = new ArrayList<Local>();

        if(lista!=null && CurrentLocation != null){
            for(Local i: lista){
                aux.setLatitude(i.getLatitud());
                aux.setLongitude(i.getLongitud());
                i.setDistancia(CurrentLocation.distanceTo(aux));
                aux.reset();
            }
            while(nlocales>count && !lista.isEmpty()){
                retorno.add(obtenerMasCercano(lista));
                lista.remove(obtenerMasCercano(lista));
                count++;
            }
        }else {
            Toast.makeText(getContext(), "Lista o Localizacion nula UwU ", Toast.LENGTH_SHORT).show();
        }
        locales = retorno;
        agregarLocales(retorno);
    }
    public Local obtenerMasCercano(List<Local> locales){
        Local localCercano= locales.get(0);
        for(Local p: locales){
            if(p.getDistancia()<localCercano.getDistancia()){
                localCercano=p;
            }
        }
        return localCercano;
    }
}