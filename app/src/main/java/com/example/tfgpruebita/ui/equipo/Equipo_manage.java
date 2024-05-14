package com.example.tfgpruebita.ui.equipo;

import static com.google.common.reflect.Reflection.getPackageName;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgpruebita.MainActivity;
import com.example.tfgpruebita.R;
import com.example.tfgpruebita.adapter.JugadorAdapter;
import com.example.tfgpruebita.databinding.FragmentEquipoManageBinding;
import com.example.tfgpruebita.modelo.Jugador;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipo_manage extends Fragment {

    private static final String FORMACION_433 = "4-3-3";
    private static final String FORMACION_442 = "4-4-2";
    private static final String FORMACION_343 = "3-4-3";

    private FirebaseFirestore db;

    ImageView delantero2;
    ImageView delantero1;
    ImageView delantero3;
    ImageView medio1;
    ImageView medio2;
    ImageView medio3;
    ImageView medio5;
    ImageView medio4;
    ImageView defensa2;
    ImageView defensa1;
    ImageView defensa4;
    ImageView defensa3;
    ImageView defensa5;
    ImageView portero;
    TextView txtdelantero2;
    TextView txtdelantero1;
    TextView txtdelantero3;
    TextView txtmedio1;
    TextView txtmedio2;
    TextView txtmedio3;
    TextView txtmedio5;
    TextView txtmedio4;
    TextView txtdefensa2;
    TextView txtdefensa3;
    TextView txtdefensa4;
    TextView txtdefensa1;
    TextView txtdefensa5;
    TextView txtportero;

    private ArrayList<TextView> listaJugadoresPartido = new ArrayList<>();

    private ArrayList<String> listaJugaSimular = new ArrayList<>();


    public Equipo_manage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(requireContext(), "Para empezar, ¡elige la formación!", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEquipoManageBinding binding = FragmentEquipoManageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        Log.e("Equipo_manage: ", db.toString());

        delantero2 = binding.delantero2;
        delantero1 = binding.delantero1;
        delantero3 = binding.delantero3;
        medio1 = binding.medio1;
        medio2 = binding.medio2;
        medio3 = binding.medio3;
        medio5 = binding.medio5;
        medio4 = binding.medio4;
        defensa2 = binding.defensa2;
        defensa1 = binding.defensa1;
        defensa4 = binding.defensa4;
        defensa3 = binding.defensa3;
        defensa5 = binding.defensa5;
        portero = binding.portero;
        txtdelantero2 = binding.txtdelantero2;
        txtdelantero1 = binding.txtdelantero1;
        txtdelantero3 = binding.txtdelantero3;
        txtmedio1 = binding.txtmedio1;
        txtmedio2 = binding.txtmedio2;
        txtmedio3 = binding.txtmedio3;
        txtmedio5 = binding.txtmedio5;
        txtmedio4 = binding.txtmedio4;
        txtdefensa2 = binding.txtdefensa2;
        txtdefensa3 = binding.txtdefensa3;
        txtdefensa4 = binding.txtdefensa4;
        txtdefensa1 = binding.txtdefensa1;
        txtdefensa5 = binding.txtdefensa5;
        txtportero = binding.txtportero;

        portero.setOnClickListener(v -> mostrarMenuPorteros(v, txtportero, portero));
        defensa1.setOnClickListener(v -> mostrarMenuDefensas(v, txtdefensa1, defensa1));
        defensa2.setOnClickListener(v -> mostrarMenuDefensas(v, txtdefensa2, defensa2));
        defensa3.setOnClickListener(v -> mostrarMenuDefensas(v, txtdefensa3, defensa3));
        defensa4.setOnClickListener(v -> mostrarMenuDefensas(v, txtdefensa4, defensa4));
        defensa5.setOnClickListener(v -> mostrarMenuDefensas(v, txtdefensa5, defensa5));
        medio1.setOnClickListener(v -> mostrarMenuMedios(v, txtmedio1, medio1));
        medio2.setOnClickListener(v -> mostrarMenuMedios(v, txtmedio2, medio2));
        medio3.setOnClickListener(v -> mostrarMenuMedios(v, txtmedio3, medio3));
        medio4.setOnClickListener(v -> mostrarMenuMedios(v, txtmedio4, medio4));
        medio5.setOnClickListener(v -> mostrarMenuMedios(v, txtmedio5, medio5));
        delantero1.setOnClickListener(v -> mostrarMenuDelanteros(v, txtdelantero1, delantero1));
        delantero2.setOnClickListener(v -> mostrarMenuDelanteros(v, txtdelantero2, delantero2));
        delantero3.setOnClickListener(v -> mostrarMenuDelanteros(v, txtdelantero3, delantero3));

        portero.setEnabled(false);
        defensa1.setEnabled(false);
        defensa2.setEnabled(false);
        defensa3.setEnabled(false);
        defensa4.setEnabled(false);
        defensa5.setEnabled(false);
        medio1.setEnabled(false);
        medio2.setEnabled(false);
        medio3.setEnabled(false);
        medio4.setEnabled(false);
        medio5.setEnabled(false);
        delantero1.setEnabled(false);
        delantero2.setEnabled(false);
        delantero3.setEnabled(false);

        listaJugadoresPartido.add(txtdelantero1);
        listaJugadoresPartido.add(txtdelantero2);
        listaJugadoresPartido.add(txtdelantero3);
        listaJugadoresPartido.add(txtmedio1);
        listaJugadoresPartido.add(txtmedio2);
        listaJugadoresPartido.add(txtmedio3);
        listaJugadoresPartido.add(txtmedio4);
        listaJugadoresPartido.add(txtmedio5);
        listaJugadoresPartido.add(txtdefensa1);
        listaJugadoresPartido.add(txtdefensa2);
        listaJugadoresPartido.add(txtdefensa3);
        listaJugadoresPartido.add(txtdefensa4);
        listaJugadoresPartido.add(txtdefensa5);
        listaJugadoresPartido.add(txtportero);

        Button button = binding.button;
        Button button1 = binding.btnGuardar;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaFormaciones(v, button);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                for (TextView txt : listaJugadoresPartido) {
                    if (!txt.getText().toString().equals("Elige")) {
                        listaJugaSimular.add(txt.getText().toString());
                    } else {
                        Toast.makeText(requireContext(), "No puedes guardar hasta tener todos los jugadores seleccionados", Toast.LENGTH_LONG).show();
                    }
                }
                MainActivity.lista = listaJugaSimular;
                Log.i("once: ", listaJugaSimular.toString());
            }
        });

        return root;
    }

    private void mostrarMenuDelanteros(View v, TextView textView, ImageView imageView) {
        db.collection("jugadores")
                .whereEqualTo("Posicion", "Delantero")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> jugadoresList = queryDocumentSnapshots.getDocuments();
                    Log.i("Equipo_manage", "Cantidad de delanteros: " + jugadoresList.size());
                    Collections.shuffle(jugadoresList);
                    List<DocumentSnapshot> jugadoresLimitados = jugadoresList.subList(0, Math.min(jugadoresList.size(), 5));

                    Dialog dialog = new Dialog(requireContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_custom_players);

                    RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewPlayers);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);

                    JugadorAdapter adapter = new JugadorAdapter(jugadoresLimitados, new JugadorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String nombreJugador, Dialog dialog) {
                            textView.setText(nombreJugador);
                            String nombreJugadorLower = nombreJugador.toLowerCase();
                            int idDrawable = getResources().getIdentifier(nombreJugadorLower, "drawable", requireActivity().getPackageName());
                            imageView.setImageResource(idDrawable);
                            imageView.setEnabled(false);
                            if (dialog != null) {
                                dialog.dismiss();
                            } else {
                                Log.e("Equipo_manage", "El objeto Dialog es nulo en onItemClick");
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);

                    dialog.show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Error", "Error al obtener delanteros: " + e.getMessage());
                    e.printStackTrace();
                });
    }


    private void mostrarMenuMedios(View v, TextView textView, ImageView imageView) {
        db.collection("jugadores")
                .whereEqualTo("Posicion", "Mediocentro")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> jugadoresList = queryDocumentSnapshots.getDocuments();
                    Log.i("Equipo_manage", "Cantidad de delanteros: " + jugadoresList.size());
                    Collections.shuffle(jugadoresList);
                    List<DocumentSnapshot> jugadoresLimitados = jugadoresList.subList(0, Math.min(jugadoresList.size(), 5));

                    Dialog dialog = new Dialog(requireContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_custom_players);

                    RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewPlayers);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);

                    JugadorAdapter adapter = new JugadorAdapter(jugadoresLimitados, new JugadorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String nombreJugador, Dialog dialog) {
                            textView.setText(nombreJugador);
                            String nombreJugadorLower = nombreJugador.toLowerCase();
                            int idDrawable = getResources().getIdentifier(nombreJugadorLower, "drawable", requireActivity().getPackageName());
                            imageView.setImageResource(idDrawable);
                            imageView.setEnabled(false);
                            if (dialog != null) {
                                dialog.dismiss();
                            } else {
                                Log.e("Equipo_manage", "El objeto Dialog es nulo en onItemClick");
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);

                    dialog.show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Error", "Error al obtener delanteros: " + e.getMessage());
                    e.printStackTrace();
                });
    }

    private void mostrarMenuDefensas(View v, TextView textView, ImageView imageView) {
        db.collection("jugadores")
                .whereEqualTo("Posicion", "Defensa")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> jugadoresList = queryDocumentSnapshots.getDocuments();
                    Log.i("Equipo_manage", "Cantidad de delanteros: " + jugadoresList.size());
                    Collections.shuffle(jugadoresList);
                    List<DocumentSnapshot> jugadoresLimitados = jugadoresList.subList(0, Math.min(jugadoresList.size(), 5));

                    Dialog dialog = new Dialog(requireContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_custom_players);

                    RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewPlayers);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);

                    JugadorAdapter adapter = new JugadorAdapter(jugadoresLimitados, new JugadorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String nombreJugador, Dialog dialog) {
                            textView.setText(nombreJugador);
                            String nombreJugadorLower = nombreJugador.toLowerCase();
                            int idDrawable = getResources().getIdentifier(nombreJugadorLower, "drawable", requireActivity().getPackageName());
                            imageView.setImageResource(idDrawable);
                            imageView.setEnabled(false);
                            if (dialog != null) {
                                dialog.dismiss();
                            } else {
                                Log.e("Equipo_manage", "El objeto Dialog es nulo en onItemClick");
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);

                    dialog.show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Error", "Error al obtener delanteros: " + e.getMessage());
                    e.printStackTrace();
                });
    }

    private void mostrarMenuPorteros(View v, TextView txtportero, ImageView imageView) {
        db.collection("jugadores")
                .whereEqualTo("Posicion", "Portero")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> jugadoresList = queryDocumentSnapshots.getDocuments();
                    Log.i("Equipo_manage", "Cantidad de delanteros: " + jugadoresList.size());
                    Collections.shuffle(jugadoresList);
                    List<DocumentSnapshot> jugadoresLimitados = jugadoresList.subList(0, Math.min(jugadoresList.size(), 5));

                    Dialog dialog = new Dialog(requireContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_custom_players);

                    RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewPlayers);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);

                    JugadorAdapter adapter = new JugadorAdapter(jugadoresLimitados, new JugadorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String nombreJugador, Dialog dialog) {
                            txtportero.setText(nombreJugador);
                            String nombreJugadorLower = nombreJugador.toLowerCase();
                            int idDrawable = getResources().getIdentifier(nombreJugadorLower, "drawable", requireActivity().getPackageName());
                            imageView.setImageResource(idDrawable);
                            imageView.setEnabled(false);
                            if (dialog != null) {
                                dialog.dismiss();
                            } else {
                                Log.e("Equipo_manage", "El objeto Dialog es nulo en onItemClick");
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);

                    dialog.show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Error", "Error al obtener delanteros: " + e.getMessage());
                    e.printStackTrace();
                });
    }

    private void listaFormaciones(View view, Button button) {
        Log.d("lista", "lista");
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.formacion_menu, popupMenu.getMenu());
        Log.d("lista1", "lista1");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String formacion = item.getTitle().toString();
                switch (formacion) {
                    case FORMACION_433:
                        Toast.makeText(requireContext(), "FORMACION ELEGIDA!", Toast.LENGTH_SHORT).show();
                        ajustarMargenesFormacion433();
                        button.setText("4-3-3");
                        button.setEnabled(false);
                        portero.setEnabled(true);
                        defensa1.setEnabled(true);
                        defensa2.setEnabled(true);
                        defensa3.setEnabled(true);
                        defensa4.setEnabled(true);
                        defensa5.setEnabled(true);
                        medio1.setEnabled(true);
                        medio2.setEnabled(true);
                        medio3.setEnabled(true);
                        medio4.setEnabled(true);
                        medio5.setEnabled(true);
                        delantero1.setEnabled(true);
                        delantero2.setEnabled(true);
                        delantero3.setEnabled(true);
                        return true;
                    case FORMACION_442:
                        Toast.makeText(requireContext(), "FORMACION ELEGIDA!", Toast.LENGTH_SHORT).show();
                        ajustarMargenesFormacion442();
                        button.setText("4-4-2");
                        button.setEnabled(false);
                        portero.setEnabled(true);
                        defensa1.setEnabled(true);
                        defensa2.setEnabled(true);
                        defensa3.setEnabled(true);
                        defensa4.setEnabled(true);
                        defensa5.setEnabled(true);
                        medio1.setEnabled(true);
                        medio2.setEnabled(true);
                        medio3.setEnabled(true);
                        medio4.setEnabled(true);
                        medio5.setEnabled(true);
                        delantero1.setEnabled(true);
                        delantero2.setEnabled(true);
                        delantero3.setEnabled(true);
                        return true;
                    case FORMACION_343:
                        Toast.makeText(requireContext(), "FORMACION ELEGIDA!", Toast.LENGTH_SHORT).show();
                        ajustarMargenesFormacion343();
                        button.setText("3-4-3");
                        button.setEnabled(false);
                        portero.setEnabled(true);
                        defensa1.setEnabled(true);
                        defensa2.setEnabled(true);
                        defensa3.setEnabled(true);
                        defensa4.setEnabled(true);
                        defensa5.setEnabled(true);
                        medio1.setEnabled(true);
                        medio2.setEnabled(true);
                        medio3.setEnabled(true);
                        medio4.setEnabled(true);
                        medio5.setEnabled(true);
                        delantero1.setEnabled(true);
                        delantero2.setEnabled(true);
                        delantero3.setEnabled(true);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void ajustarMargenesFormacion433() {
        delantero2.setVisibility(View.VISIBLE);
        delantero1.setVisibility(View.VISIBLE);
        delantero3.setVisibility(View.VISIBLE);
        medio1.setVisibility(View.VISIBLE);
        medio2.setVisibility(View.VISIBLE);
        medio3.setVisibility(View.VISIBLE);
        medio5.setVisibility(View.INVISIBLE);
        medio4.setVisibility(View.INVISIBLE);
        defensa2.setVisibility(View.VISIBLE);
        defensa3.setVisibility(View.VISIBLE);
        defensa1.setVisibility(View.VISIBLE);
        defensa4.setVisibility(View.VISIBLE);
        defensa5.setVisibility(View.INVISIBLE);
        txtdelantero2.setVisibility(View.VISIBLE);
        txtdelantero1.setVisibility(View.VISIBLE);
        txtdelantero3.setVisibility(View.VISIBLE);
        txtmedio1.setVisibility(View.VISIBLE);
        txtmedio2.setVisibility(View.VISIBLE);
        txtmedio3.setVisibility(View.VISIBLE);
        txtmedio5.setVisibility(View.INVISIBLE);
        txtmedio4.setVisibility(View.INVISIBLE);
        txtdefensa2.setVisibility(View.VISIBLE);
        txtdefensa3.setVisibility(View.VISIBLE);
        txtdefensa1.setVisibility(View.VISIBLE);
        txtdefensa4.setVisibility(View.VISIBLE);
        txtdefensa5.setVisibility(View.INVISIBLE);
        listaJugadoresPartido.remove(txtmedio5);
        listaJugadoresPartido.remove(txtmedio4);
        listaJugadoresPartido.remove(txtdefensa5);
    }

    private void ajustarMargenesFormacion442() {
        delantero1.setVisibility(View.VISIBLE);
        delantero3.setVisibility(View.VISIBLE);
        delantero2.setVisibility(View.INVISIBLE);
        medio1.setVisibility(View.INVISIBLE);
        medio2.setVisibility(View.VISIBLE);
        medio3.setVisibility(View.VISIBLE);
        medio5.setVisibility(View.VISIBLE);
        medio4.setVisibility(View.VISIBLE);
        defensa2.setVisibility(View.VISIBLE);
        defensa3.setVisibility(View.VISIBLE);
        defensa1.setVisibility(View.VISIBLE);
        defensa4.setVisibility(View.VISIBLE);
        defensa5.setVisibility(View.INVISIBLE);
        txtdelantero2.setVisibility(View.INVISIBLE);
        txtdelantero1.setVisibility(View.VISIBLE);
        txtdelantero3.setVisibility(View.VISIBLE);
        txtmedio1.setVisibility(View.INVISIBLE);
        txtmedio2.setVisibility(View.VISIBLE);
        txtmedio3.setVisibility(View.VISIBLE);
        txtmedio5.setVisibility(View.VISIBLE);
        txtmedio4.setVisibility(View.VISIBLE);
        txtdefensa2.setVisibility(View.VISIBLE);
        txtdefensa3.setVisibility(View.VISIBLE);
        txtdefensa1.setVisibility(View.VISIBLE);
        txtdefensa4.setVisibility(View.VISIBLE);
        txtdefensa5.setVisibility(View.INVISIBLE);
        listaJugadoresPartido.remove(txtdelantero2);
        listaJugadoresPartido.remove(txtmedio1);
        listaJugadoresPartido.remove(txtdefensa5);
    }

    private void ajustarMargenesFormacion343() {
        delantero2.setVisibility(View.VISIBLE);
        delantero1.setVisibility(View.VISIBLE);
        delantero3.setVisibility(View.VISIBLE);
        medio1.setVisibility(View.INVISIBLE);
        medio2.setVisibility(View.VISIBLE);
        medio3.setVisibility(View.VISIBLE);
        medio5.setVisibility(View.VISIBLE);
        medio4.setVisibility(View.VISIBLE);
        defensa2.setVisibility(View.INVISIBLE);
        defensa3.setVisibility(View.INVISIBLE);
        defensa1.setVisibility(View.VISIBLE);
        defensa4.setVisibility(View.VISIBLE);
        defensa5.setVisibility(View.VISIBLE);
        txtdelantero2.setVisibility(View.VISIBLE);
        txtdelantero1.setVisibility(View.VISIBLE);
        txtdelantero3.setVisibility(View.VISIBLE);
        txtmedio1.setVisibility(View.INVISIBLE);
        txtmedio2.setVisibility(View.VISIBLE);
        txtmedio3.setVisibility(View.VISIBLE);
        txtmedio5.setVisibility(View.VISIBLE);
        txtmedio4.setVisibility(View.VISIBLE);
        txtdefensa2.setVisibility(View.INVISIBLE);
        txtdefensa3.setVisibility(View.INVISIBLE);
        txtdefensa1.setVisibility(View.VISIBLE);
        txtdefensa4.setVisibility(View.VISIBLE);
        txtdefensa5.setVisibility(View.VISIBLE);
        listaJugadoresPartido.remove(txtmedio1);
        listaJugadoresPartido.remove(txtdefensa2);
        listaJugadoresPartido.remove(txtdefensa3);
    }

}