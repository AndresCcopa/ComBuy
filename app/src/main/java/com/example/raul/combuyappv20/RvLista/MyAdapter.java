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
import com.example.raul.combuyappv20.data.Remota.ItemRetrofit;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
    private List<Item> mydata;

    public MyAdapter(List<Item> mydata) {
        this.mydata = mydata;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemproducto, parent, false);
        MyHolder vh = new MyHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //TODO: SET DATA :'V
        TextView des=(TextView) holder.myitem.findViewById(R.id.tv_descripcionproducto);
        TextView pre=(TextView) holder.myitem.findViewById(R.id.tv_precioproducto);
        TextView nom=(TextView) holder.myitem.findViewById(R.id.tv_nombretienda);
        TextView stc=(TextView) holder.myitem.findViewById(R.id.tv_stockproducto);
        des.setText(mydata.get(position).getIdproductolocal().getDescripcion());
        pre.setText(String.valueOf(mydata.get(position).getPrecio()));
        stc.setText(String.valueOf(mydata.get(position).getStock()));
        nom.setText(mydata.get(position).getIdlocalnegocio().getNombrenegocio());
    }

    @Override
    public int getItemCount() {
        if(mydata!=null)
            return mydata.size();
        else
            return 0;
    }
    public void swap(List<Item> datas)
    {
        if(datas == null || datas.size()==0)
            return;
        if (mydata.size()>0)
        {
            mydata.clear();
        }
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