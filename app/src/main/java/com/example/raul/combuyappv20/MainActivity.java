package com.example.raul.combuyappv20;
import android.app.AlertDialog;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.example.raul.combuyappv20.RvLista.RvFragment;
import com.example.raul.combuyappv20.data.Local.Item;
import com.example.raul.combuyappv20.data.Remota.ItemRetrofit;

import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private AlertDialog dialogogps;

    private FragmentStatePagerAdapter mfragAdapter;
    private PagerSlidingTabStrip tab;

    private Button buscar;
    private EditText input;
    private ViewPager panel;
    private String consulta;
    private LocationManager mlocManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.et_input);
        buscar= findViewById(R.id.btn_busqueda);
        panel = findViewById(R.id.vp_panelinformativo);
/*
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "Activar datos", Toast.LENGTH_SHORT).show();
        }else if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(this, "Activar GPS", Toast.LENGTH_SHORT).show();
        }else {*/
            mfragAdapter = new MyPagerAdapter(getSupportFragmentManager());
            panel.setAdapter(mfragAdapter);
            panel.setPageTransformer(true,new RotateUpTransformer());

            tab = (PagerSlidingTabStrip) findViewById(R.id.pager_header);
            tab.setShouldExpand(true);
            tab.setViewPager(panel);
            tab.setTextSize(50);
            buscar.setOnClickListener(this);
  //      }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_busqueda){
            consulta = input.getText().toString();

            if(consulta==null){
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