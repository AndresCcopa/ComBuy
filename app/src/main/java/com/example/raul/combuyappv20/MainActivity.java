package com.example.raul.combuyappv20;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.example.raul.combuyappv20.RvLista.RvFragment;
import com.example.raul.combuyappv20.data.Local.Item;
import com.example.raul.combuyappv20.data.Remota.ItemRetrofit;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Fragment fragLocalMap;

    private FragmentStatePagerAdapter mfragAdapter;
    private PagerSlidingTabStrip tab;

    private Button buscar;
    private EditText input;
    private ViewPager panel;
    private String consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_drawer_layout);
        setSupportActionBar(toolbar);

        //Cambios añadidos para el sanguchito

        input = findViewById(R.id.et_input);
        buscar= findViewById(R.id.btn_busqueda);
        buscar.setOnClickListener(this);
        panel = findViewById(R.id.vp_panelinformativo);

        consulta=input.getText().toString();
        mfragAdapter = new MyPagerAdapter(getSupportFragmentManager());
        panel.setAdapter(mfragAdapter);
        panel.setPageTransformer(true,new RotateUpTransformer());

        tab = (PagerSlidingTabStrip) findViewById(R.id.pager_header);
        tab.setShouldExpand(true);
        tab.setViewPager(panel);
        tab.setTextSize(50);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_addubication) {
            // Handle the camera action
        } else if (id == R.id.nav_searchbusiness) {
            //code here

        } else if (id == R.id.nav_searchproduct) {

        } else if (id == R.id.nav_settngs) {

        }
/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_busqueda){
            consulta = input.getText().toString();

            if(consulta.equals("") || consulta==null){
                Toast.makeText(this, "No se ingreso busqueda uwu", Toast.LENGTH_SHORT).show();
            }else {
                List<Item> Data=new ItemRetrofit().getListItems(consulta);

                MapPruebaFragment map = MapPruebaFragment.getInstance(consulta);
                map.updateMap(Data);
                RvFragment lista=RvFragment.getInstance(consulta);
                lista.updateList(Data);
            }
        }
    }
    // Niuu
    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private static int NUM_ITEMS=2;
        private FragmentManager myFragmentManager;
        private Map<Integer, String> myTags;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            myFragmentManager = fragmentManager;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return RvFragment.getInstance("");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return MapPruebaFragment.getInstance("");
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        @Override
        public CharSequence getPageTitle(int posicion){
            switch (posicion){
                case 0: return "Tiendas";
                case 1: return "Mapa";
                default: return "Page "+ posicion;
            }
        }
    }
}