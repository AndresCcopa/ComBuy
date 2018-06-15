package com.example.raul.combuyappv20;

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

import java.util.List;

import static com.example.raul.combuyappv20.utils.CombuyUtils.obtenerCercanos;

public class MapPruebaFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback,OnMapReadyCallback{

    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static MapPruebaFragment instance=null;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private GoogleMap mMap;
    private Location CurrentLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean PermisoConcedido ;
    private MapView mapView;

    private static final String ARG_PARAM1 = "param1";

    private LatLng currentPosition=mDefaultLocation;
    private String consulta;

    private List<Local> locales;
    private String LOG_TAG="FEIK";

    public MapPruebaFragment() {
        // Required empty public constructor
    }

    public static MapPruebaFragment getInstance(String param1) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        if(instance== null){
            instance = new MapPruebaFragment();
            instance.setArguments(args);
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate");
        //ObtenerPermisodeUbicacion();
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


    }
    public void updateMap(String consulta){
        this.consulta=consulta;
        mMap.clear();
        try{
            locales = new LocalRetrofit().getLocalesProducto(consulta);

            if(locales==null){
                Toast.makeText(getActivity(), "No se encontro ninguno U,U", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        obtenerLocales();
        Log.v("MAPS-Update","Este es el valor de la variable consulta -> |"+consulta+"|");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");

        ObtenerPermisodeUbicacion();
        try{
            if(PermisoConcedido){
                mapView.onResume();
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
        ObtenerUbicacion(); // Obtiene Ubicacion del dispositivo y coloca la posicion en el mapa

    }

    private void agregarMarcador(double Lat,double Lng,String nombre,String descripcion){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lat, Lng))
                .title(nombre)
                .snippet(descripcion));
    }

    private void obtenerLocales() {
        Log.v("MAPS","OBTENIENDO LOCALES");
        if(locales!=null){
            mMap.clear();
            for(Local i : locales){
                agregarMarcador(i.getLatitud(),
                                i.getLongitud(),
                                i.getNombrenegocio(),
                                i.getDescripcion());
            }
        }else{
            Toast.makeText(getContext(),"No se encontro ningun local u.u", Toast.LENGTH_LONG).show();
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
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(CurrentLocation.getLatitude(),
                                            CurrentLocation.getLongitude()), DEFAULT_ZOOM));
                            currentPosition=new LatLng(CurrentLocation.getLatitude(),CurrentLocation.getLongitude());
                            Log.v("TASK","ESTA ES LA UBICACION");
                            Log.v("MAPS","Este es el valor de la variable consulta -> |"+consulta+"|");
                            setValuesMap(consulta,CurrentLocation,5);

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

    public void setValuesMap(String consulta,Location actual,int count){

        LocalRetrofit service =new LocalRetrofit();

        if(consulta==null || consulta.isEmpty()){
            locales=obtenerCercanos(service.getListLocal(),actual,count);
        }else {

            locales = obtenerCercanos(service.getLocalesProducto(consulta), actual, count);
        }
        obtenerLocales();
    }

}
