package com.example.raul.combuyappv20;

import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.raul.combuyappv20.utils.CombuyLocal;
import com.example.raul.combuyappv20.utils.CombuyRetrofit;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import static com.example.raul.combuyappv20.utils.CombuyUtils.obtenerCercanos;
import static com.example.raul.combuyappv20.utils.CombuyUtils.test;

public class MapPruebaFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback,OnMapReadyCallback{

    //Variables de mapas
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private GoogleMap mMap;
    private Location CurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private static final int permiso = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationrequest;
    private boolean PermisoConcedido ;
    private MapView mapView;
    //private static final String nombreProducto;
    String consulta;

    SearchProductFragment.OnFragmentInteractionListener Listener;

    private List<CombuyLocal> locales;
    private String LOG_TAG="FEIK";


    public MapPruebaFragment() {

        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consulta=getArguments().getString("product");
        Log.v(LOG_TAG, "onCreate");
        ObtenerPermisodeUbicacion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_prueba, container, false);
        mapView = view.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);

        return view;
    }

    public void updateMap(String consulta){
        this.consulta=consulta;

        mMap.clear();
        locales = new CombuyRetrofit().getLocalesProducto(consulta);
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

                try {
                    MapsInitializer.initialize(getActivity().getApplicationContext());
                } catch (Exception e){
                    e.printStackTrace();
                }
                mapView.getMapAsync(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("MAPS","Este es el valor de la variable consulta -> |"+consulta+"|");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v("MAPS","EN el onmapready");
        mMap = googleMap;
        //MainActivity activity=(MainActivity) getActivity();
        //consulta=activity.nombreProducto;
        updateLocationUI();
        ObtenerUbicacion(); // Obtiene Ubicacion del dispositivo y coloca la posicion en el mapa
        Log.v("MAPS","Este es el valor de la variable consulta -> |"+consulta+"|");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //Metodos del MAPA

    private void agregarMarcador(double Lat,double Lng,String nombre,String descripcion){
        Log.v("RETRO","AGREGANDO MARCADORES");
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lat, Lng))
                .title(nombre)
                .snippet(descripcion));

    }

    private void obtenerLocales() {
        Log.v("MAPS","OBTENIENDO LOCALES");
        mMap.clear();
        if(locales!=null){
            for(CombuyLocal i : locales){
                agregarMarcador(i.getLatitud(),i.getLongitud(),i.getNombrenegocio(),i.getDescripcion());
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
                    mapView.onResume();
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

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart");
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),"onStart", Toast.LENGTH_SHORT);
        toast.show();
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
                            Log.v("TASK","ESTA ES LA UBICACION");
                            Log.v("TASK",test(CurrentLocation));
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

        CombuyRetrofit service =new CombuyRetrofit();

        if(consulta==""){
            locales=obtenerCercanos(service.getListLocal(),actual,count);
        }else {

            locales = obtenerCercanos(service.getLocalesProducto(consulta), actual, count);
        }
        obtenerLocales();
    }

}
