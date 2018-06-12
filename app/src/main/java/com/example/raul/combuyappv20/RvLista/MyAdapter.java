package com.example.raul.combuyappv20.RvLista;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raul.combuyappv20.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
    private String[] mydata;

    public MyAdapter(String[] mydata) {
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
        TextView des=(TextView) holder.myTv.findViewById(R.id.tv_descripcionproducto);
        TextView pre=(TextView) holder.myTv.findViewById(R.id.tv_precioproducto);
        TextView nom=(TextView) holder.myTv.findViewById(R.id.tv_nombreproducto);
        des.setText(mydata[position]);
        pre.setText(mydata[position]);
        nom.setText(mydata[position]);
    }

    @Override
    public int getItemCount() {
        return mydata.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public LinearLayout myTv;
        public MyHolder(LinearLayout itemView) {
            super(itemView);
            myTv=itemView;
        }
    }
}