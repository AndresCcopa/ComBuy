package com.example.raul.combuyappv20.RvLista;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raul.combuyappv20.R;
import com.example.raul.combuyappv20.data.Local.Item;
import com.example.raul.combuyappv20.data.Local.Local;
import com.example.raul.combuyappv20.data.Remota.ItemRetrofit;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterTienda extends RecyclerView.Adapter<MyAdapterTienda.MyHolder>{
    private List<Local> mydata;

    public MyAdapterTienda(List<Local> mydata) {
        this.mydata = mydata;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemtienda, parent, false);
        MyHolder vh = new MyHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //TODO: SET DATA :'V
        TextView descripcion=   (TextView) holder.myitem.findViewById(R.id.tv_descripciontienda);
        TextView telefono=      (TextView) holder.myitem.findViewById(R.id.tv_telefono);
        TextView nombretienda=  (TextView) holder.myitem.findViewById(R.id.tv_nombretienda);
        TextView inicio=        (TextView) holder.myitem.findViewById(R.id.tv_horainicio);
        TextView fin=           (TextView) holder.myitem.findViewById(R.id.tv_horafin);
        descripcion.setText(mydata.get(position).getDescripcion());
        telefono.setText(mydata.get(position).getTelefono());
        nombretienda.setText(String.valueOf(mydata.get(position).getNombrenegocio()));
        inicio.setText(mydata.get(position).getHora_inicio());
        fin.setText(mydata.get(position).getHora_fin());
    }

    @Override
    public int getItemCount() {
        if(mydata!=null)
        return mydata.size();
        else
            return 0;
    }

    public void swap(List<Local> datas)
    {
        if(datas == null || datas.size()==0)
            return;
        if (mydata != null && mydata.size()>0)
            mydata.clear();
        mydata.addAll(datas);
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public LinearLayout myitem;
        public MyHolder(LinearLayout itemView) {
            super(itemView);
            myitem=itemView;
        }
    }
}