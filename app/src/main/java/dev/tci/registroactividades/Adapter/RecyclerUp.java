package dev.tci.registroactividades.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.Modelos.Huertas;
import dev.tci.registroactividades.R;
import dev.tci.registroactividades.RecyclerViewClick;
import dev.tci.registroactividades.register;

public class RecyclerUp extends RecyclerView.Adapter<RecyclerUp.HuertasUpdateViewHolder> {

    ArrayList<FormatoCalidad> listRegistro;
    private RecyclerViewClick click;
    private Context context;

    public RecyclerUp(ArrayList<FormatoCalidad> listRegistro, RecyclerViewClick click, Context context) {
        this.listRegistro = listRegistro;
        this.click = click;
        this.context = context;
    }

    @NonNull
    @Override
    public HuertasUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.actividades_row_update,null, false);
        return new RecyclerUp.HuertasUpdateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HuertasUpdateViewHolder huertasUpdateViewHolder, int i) {
        huertasUpdateViewHolder.nombreHuerta.setText(listRegistro.get(i).getHuerta());
        huertasUpdateViewHolder.nombreProductor.setText(listRegistro.get(i).getProductor());
        huertasUpdateViewHolder.fecha.setText(listRegistro.get(i).getFecha());
        huertasUpdateViewHolder.hora.setText(listRegistro.get(i).getHora());
        Glide.with(context)
                .load(listRegistro.get(i).getBaseurl())
                .into(huertasUpdateViewHolder.foto);
    }

    @Override
    public int getItemCount() {
        return listRegistro.size();
    }

    public class HuertasUpdateViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView nombreHuerta, nombreProductor, fecha, hora;
        ImageView foto;
        public HuertasUpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreHuerta = itemView.findViewById(R.id.txtUpHuerta);
            nombreProductor = itemView.findViewById(R.id.txtUpProductor);
            fecha = itemView.findViewById(R.id.txtUpFecha);
            hora = itemView.findViewById(R.id.txtUpHora);
            foto = itemView.findViewById(R.id.imgPhoto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            click.onClick(v, getAdapterPosition());
        }
    }
}
