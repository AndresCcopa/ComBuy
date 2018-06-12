package com.example.raul.combuyappv20;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SearchProductFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private Button btnsearchproduct, btnlistaproducto, btnmapa;
    private EditText tvserchproduct;
    public String LOG_TAG = "ProdFragment";
    private String nameProduct;


    public SearchProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnsearchproduct = view.findViewById(R.id.btn_search_producto);
        tvserchproduct = view.findViewById(R.id.et_search_producto);
        btnlistaproducto = view.findViewById(R.id.btn_lista_productos);
        btnmapa = view.findViewById(R.id.btn_mapa);
        btnmapa = view.findViewById(R.id.btn_mapa);
        btnmapa.setOnClickListener(this);
        btnsearchproduct.setOnClickListener(this);
        btnlistaproducto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.btn_search_producto){
            Log.v(LOG_TAG, "Reconoce el onClick");
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),tvserchproduct.getText().toString(), Toast.LENGTH_SHORT);
            toast.show();
            mListener.onButtonSearchPress(tvserchproduct.getText().toString());
            nameProduct = tvserchproduct.getText().toString();
        }
        else if (v.getId() == R.id.btn_lista_productos){
            Log.v("BTN","hay boton?");
            mListener.onButtonListPress(nameProduct);
        }
        else if (v.getId() == R.id.btn_mapa){
            mListener.onButtonMapPress(nameProduct);
        }

        else{
            Log.v(LOG_TAG, "NO reconoce el Onclick");
        }
    }

    public interface OnFragmentInteractionListener {
        void onButtonListPress(String nombreProducto);
        void onButtonSearchPress(String nombreProducto);
        void onButtonMapPress(String nombreProducto);
    }
}
