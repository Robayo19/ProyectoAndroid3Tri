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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView recyclerViewUsers;
    private List<String> userList;
    private UserAdapter userAdapter;


    private TextView textViewLeagueName;
    private Button buttonCreateLeague;
    private Button buttonJoinLeague;

    private Button buttonAcept;
    private Button buttonCancel;
    private Button btnSalirLiga;
    private EditText editTextLeagueName;

    private TextView textViewUserEmails;

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
        recyclerViewUsers = rootView.findViewById(R.id.textViewUserEmails);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(requireContext()));

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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
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
                            buttonAcept.setVisibility(View.INVISIBLE);
                            buttonCancel.setVisibility(View.INVISIBLE);
                            List<String> usuariosEnLiga = (List<String>) document.get("usuarios_en_liga");
                            Log.e("Lista ids pre", usuariosEnLiga.toString());
                            mostrarNombresUsuariosEnLiga(usuariosEnLiga);
                        } else {
                            textViewLeagueName.setText("No estás en ninguna liga");
                            btnSalirLiga.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void mostrarNombresUsuariosEnLiga(List<String> usuariosEnLiga) {
        buttonAcept.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.INVISIBLE);
        if (!usuariosEnLiga.isEmpty()) {
            userList = new ArrayList<>(usuariosEnLiga);
            userAdapter = new UserAdapter(userList);
            recyclerViewUsers.setAdapter(userAdapter);
        }
    }



    private void crearLiga(String nombreLiga) {
        buttonAcept.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ligaName = editTextLeagueName.getText().toString().trim();
                if (!ligaName.isEmpty()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    Map<String, Object> ligaData = new HashMap<>();
                    ligaData.put("nombre", ligaName);
                    ligaData.put("creador", userId);
                    ligaData.put("usuarios_en_liga", Arrays.asList(userId));
                    db.collection("ligas").add(ligaData)
                            .addOnSuccessListener(documentReference -> {
                                editTextLeagueName.setVisibility(View.INVISIBLE);
                                Toast.makeText(requireContext(), "Liga creada exitosamente", Toast.LENGTH_SHORT).show();
                                unirseALiga(ligaName);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "Error al crear la liga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(requireContext(), "Por favor, ingrese un nombre para la liga", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLeagueName.setVisibility(View.INVISIBLE);
                buttonAcept.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void mostrarLigasDisponibles() {
        buttonAcept.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.INVISIBLE);
        editTextLeagueName.setVisibility(View.INVISIBLE);
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getEmail();

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
                                    buttonAcept.setVisibility(View.INVISIBLE);
                                    buttonCancel.setVisibility(View.INVISIBLE);
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
        buttonAcept.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.INVISIBLE);
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