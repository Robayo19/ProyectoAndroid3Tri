package com.example.tfgpruebita.adapter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfgpruebita.R;
import com.example.tfgpruebita.adapter.JugadorAdapter;
import com.example.tfgpruebita.modelo.Jugador;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<DocumentSnapshot> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datosnoticias, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList);
        recyclerView.setAdapter(adapter);

        obtenerDatosFirestore();

        return view;
    }

    private void obtenerDatosFirestore() {
        db.collection("datos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    dataList.addAll(queryDocumentSnapshots.getDocuments());
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                    Log.e("TuFragmento", "Error al obtener datos: " + e.getMessage());
                });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<DocumentSnapshot> dataList;

        public MyAdapter(List<DocumentSnapshot> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            DocumentSnapshot snapshot = dataList.get(position);
            String titulo = snapshot.getString("titulo");
            String dato = snapshot.getString("dato");
            String autor = snapshot.getString("autor");
            String imagenUrl = snapshot.getString("imagen_url");
            Glide.with(holder.itemView).load(imagenUrl).placeholder(R.drawable.user).into(holder.imageView);

            holder.tituloTextView.setText(titulo);
            holder.datoTextView.setText(dato);
            holder.autorTextView.setText(autor);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tituloTextView, datoTextView, autorTextView;
            ImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tituloTextView = itemView.findViewById(R.id.tituloTextView);
                datoTextView = itemView.findViewById(R.id.datoTextView);
                autorTextView = itemView.findViewById(R.id.autorTextView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}
