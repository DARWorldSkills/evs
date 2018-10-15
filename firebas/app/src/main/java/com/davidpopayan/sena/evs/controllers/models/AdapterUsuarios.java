package com.davidpopayan.sena.evs.controllers.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.davidpopayan.sena.evs.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.Holder> {
    private List<Usuario> usuarioList = new ArrayList<>();

    public AdapterUsuarios(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.connectData(usuarioList.get(position));

        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    usuarioList.get(holder.getAdapterPosition()).setActivado("si");
                }else {
                    usuarioList.get(holder.getAdapterPosition()).setActivado("no");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CheckBox checkBox = itemView.findViewById(R.id.cbUsuario);
        public Holder(View itemView) {
            super(itemView);
        }

        public void connectData(Usuario usuario){
            if (usuario.getActivado().equals("si")){
                checkBox.setSelected(true);
            }else {
                checkBox.setSelected(false);
            }
            checkBox.setText(usuario.getUsername());

        }

    }
}
