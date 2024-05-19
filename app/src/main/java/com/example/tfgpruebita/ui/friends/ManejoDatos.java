package com.example.tfgpruebita.ui.friends;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.tfgpruebita.MainActivity;
import com.example.tfgpruebita.R;
import com.example.tfgpruebita.databinding.FragmentEquipoManageBinding;
import com.example.tfgpruebita.databinding.FragmentFriendsBinding;
import com.example.tfgpruebita.modelo.Dato;
import com.example.tfgpruebita.modelo.Persona;
import com.example.tfgpruebita.ui.LoginRegister.Login;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ManejoDatos extends Fragment {

    private List<Dato> listaDatos = new ArrayList<>();
    private ArrayAdapter<Dato> arrayAdapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private EditText titulo, dato, autor, imagenUrl;
    private ListView listViewDatos;

    private Button btnBack;

    private FirebaseFirestore db;

    Dato datoSeleccionado;

    private static final String AÑADIR = "Añadir";
    private static final String ACTUALIZAR = "Actualizar";
    private static final String ELIMINAR = "Borrar";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cruddatos, container, false);

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.txt_titulo);
        dato = root.findViewById(R.id.txt_dato);
        autor = root.findViewById(R.id.txt_autor);
        imagenUrl = root.findViewById(R.id.txt_imagenUrl);
        listViewDatos = root.findViewById(R.id.listaDatos);
        Button button = root.findViewById(R.id.buttonDatos);

        inicializarFirestore();
        listarDatos();

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.navigation_home));

        listViewDatos.setOnItemClickListener((parent, view, position, id) -> {
            datoSeleccionado = (Dato) parent.getItemAtPosition(position);
            titulo.setText(datoSeleccionado.getTitulo());
            dato.setText(datoSeleccionado.getDato());
            autor.setText(datoSeleccionado.getAutor());
            imagenUrl.setText(datoSeleccionado.getImagenUrl());
        });

        button.setOnClickListener(this::seleccionarAccion);

        return root;
    }

    private void listarDatos() {
        db.collection("datos").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("ManejoDatos", "Error al obtener datos: " + error.getMessage());
                return;
            }

            listaDatos.clear();
            for (DocumentSnapshot document : value.getDocuments()) {
                Dato d = document.toObject(Dato.class);
                if (d != null) {
                    d.setId(document.getId());
                    listaDatos.add(d);
                }
            }
            actualizarLista();
        });
    }

    private void actualizarLista() {
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaDatos);
        listViewDatos.setAdapter(arrayAdapter);
    }

    private void inicializarFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    private void seleccionarAccion(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_crud, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            String tituloStr = titulo.getText().toString();
            String datoStr = dato.getText().toString();
            String autorStr = autor.getText().toString();
            String imagenUrlStr = imagenUrl.getText().toString();
            String eleccion = item.getTitle().toString();

            switch (eleccion) {
                case AÑADIR:
                    if (validarCampos()) {
                        agregarDato(tituloStr, datoStr, autorStr, imagenUrlStr);
                        Toast.makeText(requireContext(), "Dato agregado", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_LONG).show();
                    }
                    break;
                case ACTUALIZAR:
                    if (datoSeleccionado != null && datoSeleccionado.getId() != null) {
                        Dato d = new Dato(datoSeleccionado.getId(), tituloStr, datoStr, autorStr, imagenUrlStr);
                        actualizarDato(d);
                        limpiar();
                    } else {
                        Log.e("ManejoDatos", "datoSeleccionado es null o su ID es null");
                        Toast.makeText(requireContext(), "Selecciona un dato válido antes de actualizar", Toast.LENGTH_LONG).show();
                    }
                    break;
                case ELIMINAR:
                    if (datoSeleccionado != null && datoSeleccionado.getId() != null) {
                        eliminarDato(datoSeleccionado.getId());
                        Toast.makeText(requireContext(), "Dato eliminado", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Log.e("ManejoDatos", "datoSeleccionado es null o su ID es null");
                        Toast.makeText(requireContext(), "Selecciona un dato válido antes de eliminar", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            return true;
        });

        popupMenu.show();
    }

    private void eliminarDato(String id) {
        db.collection("datos").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Documento eliminado con éxito");
                    eliminarDatoDeLista(id);
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al eliminar documento: " + e.getMessage()));
    }

    private void eliminarDatoDeLista(String datoId) {
        for (Dato d : listaDatos) {
            if (datoId.equals(d.getId())) {
                listaDatos.remove(d);
                break;
            }
        }
        actualizarLista();
    }

    private void actualizarDato(Dato d) {
        DocumentReference docRef = db.collection("datos").document(d.getId());

        Map<String, Object> updates = new HashMap<>();
        updates.put("titulo", d.getTitulo());
        updates.put("dato", d.getDato());
        updates.put("autor", d.getAutor());
        updates.put("imagenUrl", d.getImagenUrl());

        docRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Documento actualizado con éxito");
                    Toast.makeText(requireContext(), "Dato actualizado", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al actualizar documento: " + e.getMessage()));
    }

    private void agregarDato(String titulo, String dato, String autor, String imagenUrl) {
        Dato d = new Dato(null, titulo, dato, autor, imagenUrl);

        db.collection("datos").add(d)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Agregado: ", "Documento agregado con ID: " + documentReference.getId());
                    d.setId(documentReference.getId());
                    listaDatos.add(d);
                    actualizarLista();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al agregar documento: " + e.getMessage()));
    }

    private void limpiar() {
        titulo.setText("");
        dato.setText("");
        autor.setText("");
        imagenUrl.setText("");
    }

    private boolean validarCampos() {
        return !titulo.getText().toString().isEmpty() &&
                !dato.getText().toString().isEmpty() &&
                !autor.getText().toString().isEmpty() &&
                !imagenUrl.getText().toString().isEmpty();
    }
}
