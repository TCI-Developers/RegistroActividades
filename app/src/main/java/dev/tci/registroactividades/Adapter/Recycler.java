package dev.tci.registroactividades.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import dev.tci.registroactividades.Modelos.Huertas;
import dev.tci.registroactividades.R;
import dev.tci.registroactividades.RecyclerViewClick;

public class Recycler extends RecyclerView.Adapter<Recycler.HuertasViewHolder> {
    ArrayList<Huertas> listRegistro;
    private RecyclerViewClick click;

    public Recycler(ArrayList<Huertas> listRegistro, RecyclerViewClick click) {
        this.listRegistro = listRegistro;
        this.click = click;
    }

    @NonNull
    @Override
    public HuertasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.actividades_row,null, false);
        return new HuertasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HuertasViewHolder huertasViewHolder, int i) {
        huertasViewHolder.nombreHuerta.setText("Huerta: "+listRegistro.get(i).getNombreHuerta());
        huertasViewHolder.nombreProductor.setText("Productor: "+listRegistro.get(i).getNombreProductor()+"\nContacto: "+ listRegistro.get(i).getContacto() + "\nFecha: " + listRegistro.get(i).getFecha());
    }

    @Override
    public int getItemCount() {
        return listRegistro.size();
    }

    public class HuertasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nombreHuerta, nombreProductor;

        public HuertasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreHuerta = itemView.findViewById(R.id.txtNombreHuerta);
            nombreProductor = itemView.findViewById(R.id.txtNombreProductor);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            click.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            click.onLongClick(v, getAdapterPosition());
            return true;
        }
    }
}
