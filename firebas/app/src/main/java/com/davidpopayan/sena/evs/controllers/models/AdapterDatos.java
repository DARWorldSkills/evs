package com.davidpopayan.sena.evs.controllers.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.davidpopayan.sena.evs.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.Holder> {
    List<Datos> datosList = new ArrayList<>();
    private OnItemClickListener newListener;
    public interface OnItemClickListener{
        void itemClick(int position);
    }

    public AdapterDatos(List<Datos> datosList) {
        this.datosList = datosList;
    }

    public void setNewListener(OnItemClickListener newListener) {
        this.newListener = newListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datos,parent,false);
        Holder holder = new Holder(view,newListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.connectData(datosList.get(position));
    }

    @Override
    public int getItemCount() {
        return datosList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtIdentificacion = itemView.findViewById(R.id.txtIdentificacionItem);
        TextView txtNombre = itemView.findViewById(R.id.txtNombreItem);
        TextView txtEps = itemView.findViewById(R.id.txtEpsItem);
        public Holder(View itemView, final OnItemClickListener newListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newListener!=null){
                        int position = getLayoutPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            newListener.itemClick(position);
                        }
                    }
                }
            });
        }

        public void connectData(Datos datos){
            txtIdentificacion.setText(datos.getNumeroId());
            txtNombre.setText(datos.getNombreCompleto());
            try {
                txtEps.setText(datos.getNombreEPS().substring(0,datos.getNombreEPS().length()-16));
            }catch (Exception e){

            }

        }

    }
}
