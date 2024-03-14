package com.example.tfgpruebita.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgpruebita.R;
import com.example.tfgpruebita.modelo.Jugador;

import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private List<Jugador> jugadorList;
    private Context context;

    public JugadorAdapter(Context context, List<Jugador> jugadorList) {
        this.context = context;
        this.jugadorList = jugadorList;
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_jugador, parent, false);
        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        Jugador jugador = jugadorList.get(position);
        Log.d("JugadorAdapter", "Nombre: " + jugador.getNombre());
        Log.d("JugadorAdapter", "Posición: " + jugador.getPosicion());
        Log.d("JugadorAdapter", "Equipo: " + jugador.getEquipo());

        holder.nombreJugador.setText(jugador.getNombre());
        holder.posicion.setText(jugador.getPosicion());
        holder.equipoJugador.setText(jugador.getEquipo());
    }

    @Override
    public int getItemCount() {
        return jugadorList.size();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView nombreJugador, posicion, equipoJugador;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            posicion = itemView.findViewById(R.id.posicion);
            equipoJugador = itemView.findViewById(R.id.equipoJugador);
        }
    }
}
