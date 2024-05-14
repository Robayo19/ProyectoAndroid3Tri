package com.example.tfgpruebita.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgpruebita.R;
import com.example.tfgpruebita.modelo.Jugador;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private List<DocumentSnapshot> jugadorList;
    private Context context;
    private OnItemClickListener listener;

    public JugadorAdapter(List<DocumentSnapshot> jugadorList, OnItemClickListener listener) {
        this.jugadorList = jugadorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext(); // Establecer el contexto aquí
        View view = LayoutInflater.from(context).inflate(R.layout.view_jugador, parent, false);
        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        DocumentSnapshot jugadorSnapshot = jugadorList.get(position);
        Jugador jugador = jugadorSnapshot.toObject(Jugador.class);

        if (jugador != null) {
            Log.d("JugadorAdapter", "Nombre: " + jugador.getNombre());
            Log.d("JugadorAdapter", "Posición: " + jugador.getPosicion());
            Log.d("JugadorAdapter", "Equipo: " + jugador.getEquipo());

            holder.nombreJugador.setText(jugador.getNombre());
            holder.posicion.setText(jugador.getPosicion());
            holder.equipoJugador.setText(jugador.getEquipo());

            String nombreImagen = jugador.getNombre().toLowerCase();
            int idDrawable = context.getResources().getIdentifier(nombreImagen, "drawable", context.getPackageName());
            if (idDrawable != 0) {
                holder.imageViewJugador.setImageResource(idDrawable);
            } else {
                holder.imageViewJugador.setImageResource(R.drawable.user);
            }

            holder.bind(jugador, listener);
        }
    }

    @Override
    public int getItemCount() {
        return jugadorList.size();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView nombreJugador, posicion, equipoJugador;
        ImageView imageViewJugador;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            posicion = itemView.findViewById(R.id.posicion);
            equipoJugador = itemView.findViewById(R.id.equipoJugador);
            imageViewJugador = itemView.findViewById(R.id.imageViewJugador);
        }

        public void bind(final Jugador jugador, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(jugador.getNombre(), null); // Pasar solo el nombre del jugador y null para el Dialog
                }
            });
        }
    }

    // Interfaz para manejar clics en los elementos del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(String nombreJugador, Dialog dialog);
    }
}


