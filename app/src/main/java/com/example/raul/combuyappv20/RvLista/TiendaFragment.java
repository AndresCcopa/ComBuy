package com.example.raul.combuyappv20.RvLista;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.raul.combuyappv20.R;
import com.example.raul.combuyappv20.data.Local.Local;
import com.example.raul.combuyappv20.data.Remota.LocalRetrofit;

import java.util.ArrayList;
import java.util.List;


public class TiendaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static TiendaFragment instance = null;


    //private final String TAG= getClass().getSimpleName();
    private final String TAG= "RV";
    private RecyclerView mRecyclerView;
    private MyAdapterTienda mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String consulta;
    private List<Local> items=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Local> defList;

    public TiendaFragment() {
        // Required empty public constructor
    }


    public static TiendaFragment getInstance(String param1) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        if(instance== null){
            instance = new TiendaFragment();
        }
        instance.setArguments(args);
        Log.v("RVTienda",param1);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            consulta = getArguments().getString(ARG_PARAM1);
        }
    }
    public void updateList(List<Local> reData){

        mAdapter.swap(reData);
        Toast.makeText(getActivity(), "Data Update", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tienda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defList = new LocalRetrofit().getListLocal();

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new MyAdapterTienda(items);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_listaproducto);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        preparedData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void preparedData(){

        items.clear();
        for (Local i: defList){
            items.add(i);
        }

        mAdapter.notifyDataSetChanged();
    }
}