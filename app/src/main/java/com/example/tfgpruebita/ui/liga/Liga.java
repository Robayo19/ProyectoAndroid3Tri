package com.example.tfgpruebita.ui.liga;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfgpruebita.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Liga extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private TextView textViewLeagueName;
    private Button buttonCreateLeague;
    private Button buttonJoinLeague;

    private Button buttonAcept;
    private Button buttonCancel;
    private Button btnSalirLiga;
    private EditText editTextLeagueName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liga, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textViewLeagueName = rootView.findViewById(R.id.textViewLeagueName);
        buttonCreateLeague = rootView.findViewById(R.id.buttonCreateLeague);
        buttonJoinLeague = rootView.findViewById(R.id.buttonJoinLeague);
        buttonAcept = rootView.findViewById(R.id.btnAcept);
        buttonCancel = rootView.findViewById(R.id.btnCancel);
        btnSalirLiga = rootView.findViewById(R.id.btnSalirLiga);
        editTextLeagueName = rootView.findViewById(R.id.editTextLeagueName);

        verificarLigaUsuario();

        buttonCreateLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLeagueName.setVisibility(View.VISIBLE);
                String leagueName = editTextLeagueName.getText().toString();
                crearLiga(leagueName);
            }
        });

        buttonJoinLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarLigasDisponibles();
            }
        });

        btnSalirLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salirDeLiga();
            }
        });

        return rootView;
    }

    private void verificarLigaUsuario() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("ligas")
                .whereArrayContains("usuarios_en_liga", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String nombreLiga = document.getString("nombre");
                            textViewLeagueName.setText("Liga actual: " + nombreLiga);
                            btnSalirLiga.setVisibility(View.VISIBLE);
                            buttonCreateLeague.setVisibility(View.INVISIBLE);
                            buttonJoinLeague.setVisibility(View.INVISIBLE);
                            List<String> usuariosEnLiga = (List<String>) document.get("usuarios_en_liga");
                            mostrarNombresUsuariosEnLiga(usuariosEnLiga);
                        } else {
                            textViewLeagueName.setText("No estás en ninguna liga");
                            btnSalirLiga.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void mostrarNombresUsuariosEnLiga(List<String> usuariosEnLiga) {
        for (String userId : usuariosEnLiga) {
            db.collection("usuario").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                String nombreUsuario = documentSnapshot.getString("nombre");
                                if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                                    textViewLeagueName.append("\n" + nombreUsuario);
                                } else {
                                    Log.e("Liga", "El nombre del usuario es nulo o vacío");
                                }
                            } else {
                                Log.e("Liga", "El documento de usuario no existe");
                            }
                        } else {
                            Log.e("Liga", "Error al obtener el documento de usuario: ", task.getException());
                        }
                    });
        }
    }



    private void crearLiga(String nombreLiga) {
        String userId = mAuth.getCurrentUser().getUid();

        Map<String, Object> ligaData = new HashMap<>();
        ligaData.put("nombre", nombreLiga);
        ligaData.put("creador", userId);
        ligaData.put("usuarios_en_liga", Arrays.asList(userId));
        db.collection("ligas").add(ligaData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(requireContext(), "Liga creada exitosamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error al crear la liga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void mostrarLigasDisponibles() {
        db.collection("ligas")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> ligasDisponibles = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String nombreLiga = documentSnapshot.getString("nombre");
                        ligasDisponibles.add(nombreLiga);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Ligas Disponibles");
                    builder.setItems(ligasDisponibles.toArray(new String[0]), (dialogInterface, i) -> {
                        String ligaSeleccionada = ligasDisponibles.get(i);
                        unirseALiga(ligaSeleccionada);
                    });
                    builder.show();
                })

                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error al obtener las ligas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void unirseALiga(String nombreLiga) {
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("ligas").whereEqualTo("nombre", nombreLiga).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        String ligaId = documentSnapshot.getId();

                        db.collection("ligas").document(ligaId)
                                .update("usuarios_en_liga", FieldValue.arrayUnion(userId))
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(requireContext(), "Te has unido a la liga exitosamente", Toast.LENGTH_SHORT).show();
                                    textViewLeagueName.setText("Liga actual: " + nombreLiga);
                                    btnSalirLiga.setVisibility(View.VISIBLE);
                                    buttonCreateLeague.setVisibility(View.INVISIBLE);
                                    buttonJoinLeague.setVisibility(View.INVISIBLE);
                                    mostrarNombresUsuariosEnLiga(Arrays.asList(userId));
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(requireContext(), "Error al unirse a la liga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(requireContext(), "La liga seleccionada no existe", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error al obtener la liga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void salirDeLiga() {
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("ligas")
                .whereArrayContains("usuarios_en_liga", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String ligaId = document.getId();
                            db.collection("ligas").document(ligaId)
                                    .update("usuarios_en_liga", FieldValue.arrayRemove(userId))
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(requireContext(), "Has salido de la liga exitosamente", Toast.LENGTH_SHORT).show();
                                        textViewLeagueName.setText("No estás en ninguna liga");
                                        btnSalirLiga.setVisibility(View.INVISIBLE);
                                        buttonJoinLeague.setVisibility(View.VISIBLE);
                                        buttonCreateLeague.setVisibility(View.VISIBLE);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(requireContext(), "Error al salir de la liga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(requireContext(), "No estás en ninguna liga", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error al obtener información de la liga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}