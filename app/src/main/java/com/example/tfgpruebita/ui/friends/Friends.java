package com.example.tfgpruebita.ui.friends;

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

public class Friends extends Fragment {

    private List<Persona> listaPersona = new ArrayList<>();
    private ArrayAdapter<Persona> arrayAdapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private EditText nombre, correo, contrasena;
    private ListView listViewUsuarios;

    private Button btnBack;

    private FirebaseFirestore db;

    Persona personaSeleccionada;

    private static final String AÑADIR = "Añadir";
    private static final String ACTUALIZAR = "Actualizar";
    private static final String ELIMINAR = "Borrar";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFriendsBinding binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String idUser = user.getUid();

        btnBack = binding.btnBack;
        nombre = binding.txtNombrePersona;
        correo = binding.txtCorreoPersona;
        contrasena = binding.txtContrasenaPersona;
        listViewUsuarios = binding.listaDatos;
        Button button = binding.buttonFriends;

        inicializarFirestore();
        listarDatos();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_home);
            }
        });

        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSeleccionada = (Persona) parent.getItemAtPosition(position);
                nombre.setText(personaSeleccionada.getNombre());
                correo.setText(personaSeleccionada.getCorreo());
                contrasena.setText(personaSeleccionada.getContrasena());
                Log.e("Impresion persona:", personaSeleccionada.toString());
            }
        });

        button.setOnClickListener(v -> seleccionarAccion(v));

        return root;
    }

    private void listarDatos() {
        db.collection("usuario").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Friends", "Error al obtener datos: " + error.getMessage());
                return;
            }

            listaPersona.clear();
            for (DocumentSnapshot document : value.getDocuments()) {
                Persona p = document.toObject(Persona.class);
                if (p != null) {
                    p.setId(document.getId());
                    listaPersona.add(p);
                }
            }
            actualizarLista();
        });
    }

    private void actualizarLista() {
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaPersona);
        listViewUsuarios.setAdapter(arrayAdapter);
    }

    private void inicializarFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    private void seleccionarAccion(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_crud, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            String nombre1 = nombre.getText().toString();
            String correo1 = correo.getText().toString();
            String contra1 = contrasena.getText().toString();
            String eleccion = item.getTitle().toString();

            switch (eleccion) {
                case AÑADIR:
                    if (validarCampos()) {
                        agregarPersona(nombre1, correo1, contra1);
                        Toast.makeText(requireContext(), "Agregar", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_LONG).show();
                    }
                    break;
                case ACTUALIZAR:
                    Persona p = new Persona();
                    if(personaSeleccionada != null && personaSeleccionada.getId() != null) {
                        p.setId(personaSeleccionada.getId());
                        p.setNombre(nombre.getText().toString().trim());
                        p.setCorreo(correo.getText().toString().trim());
                        p.setContrasena(contrasena.getText().toString().trim());
                        actualizaPersona(p);
                        limpiar();
                    } else {
                        Log.e("Friends", "personaSeleccionada es null o su ID es null");
                        Toast.makeText(requireContext(), "Selecciona una persona válida antes de actualizar", Toast.LENGTH_LONG).show();
                    }
                    break;

                case ELIMINAR:
                    if (personaSeleccionada != null && personaSeleccionada.getId() != null) {
                        eliminarPersona(personaSeleccionada.getId());
                        Toast.makeText(requireContext(), "Eliminar", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Log.e("Friends", "personaSeleccionada es null o su ID es null");
                        Toast.makeText(requireContext(), "Selecciona una persona válida antes de eliminar", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            return true;
        });

        popupMenu.show();
    }

    private void eliminarPersona(String id) {
        db.collection("usuario").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Documento eliminado con éxito");
                    eliminarPersonaDeLista(id);
                    Toast.makeText(requireContext(), "Eliminar", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al eliminar documento: " + e.getMessage());
                });
    }

    private void eliminarPersonaDeLista(String personaId) {
        for (Persona p : listaPersona) {
            if (personaId.equals(p.getId())) {
                listaPersona.remove(p);
                break;
            }
        }
        actualizarLista();
    }

    private void actualizaPersona(Persona p) {
        DocumentReference docRef = db.collection("usuario").document(p.getId());

        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", p.getNombre());
        updates.put("correo", p.getCorreo());
        updates.put("contrasena", p.getContrasena());

        docRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Documento actualizado con éxito");
                    Toast.makeText(requireContext(), "Guardar", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al actualizar documento: " + e.getMessage());
                });
    }

    private void agregarPersona(String nombre, String correo, String contra) {
        Persona p = new Persona();
        p.setNombre(nombre);
        p.setCorreo(correo);
        p.setContrasena(contra);

        db.collection("usuario").add(p)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Agregado: ", "Documento agregado con ID: " + documentReference.getId());
                    p.setId(documentReference.getId());
                    listaPersona.add(p);
                    actualizarLista();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al agregar documento: " + e.getMessage()));
    }

    private void limpiar() {
        nombre.setText("");
        correo.setText("");
        contrasena.setText("");
    }

    private boolean validarCampos() {
        return !nombre.getText().toString().isEmpty() &&
                !correo.getText().toString().isEmpty() &&
                !contrasena.getText().toString().isEmpty();
    }
}
