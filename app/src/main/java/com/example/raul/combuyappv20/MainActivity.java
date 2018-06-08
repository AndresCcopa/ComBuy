package com.example.raul.combuyappv20;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import static com.example.raul.combuyappv20.R.layout.activity_maps;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchProductFragment.OnFragmentInteractionListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Fragment fragLocalMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        ToolbarFragment toolbarFragment = new ToolbarFragment();
        /*
        fragmentTransaction2.add(R.id.body_container,toolbarFragment);
        fragmentTransaction2.commit();
        */
        fragmentTransaction2.add(R.id.layout_toolbar_container, toolbarFragment);
        fragmentTransaction2.commit();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        boolean FTransaction = false;
        Fragment fragmentNavbar = null;
        Fragment fragmentMap = null;
        Bundle args = new Bundle();
        args.putString("product","");

        if (id == R.id.nav_addubication) {
            // Handle the camera action
        } else if (id == R.id.nav_searchbusiness) {
            fragmentNavbar = new SearchProductFragment();
            fragmentMap = new MapPruebaFragment();
            fragmentMap.setArguments(args);
            fragLocalMap=fragmentMap;
            FTransaction = true;

        } else if (id == R.id.nav_searchproduct) {

        } else if (id == R.id.nav_settngs) {

        }

        if(FTransaction){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.layout_toolbar_container,fragmentNavbar);
            fragmentTransaction.replace(R.id.layout_map_container,fragmentMap).commit();
            fragmentTransaction.addToBackStack(null);
            /*
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.layout_map_container, fragmentNavbar).commit();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.map_container,fragmentMap).commit();

            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.layout_body_container, fragmentNavbar).commit();

            fragmentTransaction.addToBackStack(null);
            */

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onButtonSearchPress(String nombreProducto) {


        try {
            if (nombreProducto == null) {
                nombreProducto = "-";
            }
            Log.v("MAINACTIVITY", "El valor de nombre producto es -> |" + nombreProducto + "|");
            Toast.makeText(this, "Javier" + nombreProducto, Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putString("product", nombreProducto);

            MapPruebaFragment map= (MapPruebaFragment) getSupportFragmentManager().findFragmentById(R.id.layout_map_container);
            map.updateMap(nombreProducto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        }



/*
        OnMapReadyCallback map=new MapPruebaFragment;
        map.onMapReady();
*/
        //FragmentTransaction ft=this.getSupportFragmentManager().beginTransaction();


    }

