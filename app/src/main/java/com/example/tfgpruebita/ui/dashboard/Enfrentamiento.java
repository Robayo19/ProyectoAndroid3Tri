package com.example.tfgpruebita.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tfgpruebita.MainActivity;
import com.example.tfgpruebita.R;
import com.example.tfgpruebita.databinding.FragmentDashboardBinding;
import com.example.tfgpruebita.modelo.Jugador;
import com.example.tfgpruebita.ui.home.HomeFragment;

import java.util.ArrayList;

public class Enfrentamiento extends Fragment {

    String equipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDashboardBinding binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button button = binding.simular;
        TextView jugador1TextView = binding.JugadorElegido1;
        TextView jugador2TextView = binding.JugadorElegido2;
        TextView jugador3TextView = binding.JugadorElegido3;
        TextView jugador4TextView = binding.JugadorElegido4;
        TextView jugador5TextView = binding.JugadorElegido5;
        TextView jugador6TextView = binding.JugadorElegido6;
        TextView jugador7TextView = binding.JugadorElegido7;
        TextView jugador8TextView = binding.JugadorElegido8;
        TextView jugador9TextView = binding.JugadorElegido9;
        TextView jugador10TextView = binding.JugadorElegido10;
        TextView jugador11TextView = binding.JugadorElegido11;
        equipo = HomeFragment.equipoPasar;
        ImageView equipoLocal = binding.equipoLocal;

        actualizarImagenLocal(equipo, equipoLocal);

        TextView jugador1Definido = binding.JugadorDefinido1;
        TextView jugador2Definido = binding.JugadorDefinido2;
        TextView jugador3Definido = binding.JugadorDefinido3;
        TextView jugador4Definido = binding.JugadorDefinido4;
        TextView jugador5Definido = binding.JugadorDefinido5;
        TextView jugador6Definido = binding.JugadorDefinido6;
        TextView jugador7Definido = binding.JugadorDefinido7;
        TextView jugador8Definido = binding.JugadorDefinido8;
        TextView jugador9Definido = binding.JugadorDefinido9;
        TextView jugador10Definido = binding.JugadorDefinido10;
        TextView jugador11Definido = binding.JugadorDefinido11;

        ImageView j1 = binding.imagej1;
        ImageView j2 = binding.imagej2;
        ImageView j3 = binding.imagej3;
        ImageView j4 = binding.imagej4;
        ImageView j5 = binding.imagej5;
        ImageView j6 = binding.imagej6;
        ImageView j7 = binding.imagej7;
        ImageView j8 = binding.imagej8;
        ImageView j9 = binding.imagej9;
        ImageView j10 = binding.imagej10;
        ImageView j11 = binding.imagej11;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idDrawable;
                String jugadorLow;
                for (int i = 0; i < MainActivity.lista.size(); i++) {
                    String jugador = MainActivity.lista.get(i);
                    switch (i) {
                        case 0:
                            jugador1TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j1.setImageResource(idDrawable);
                            break;
                        case 1:
                            jugador2TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j2.setImageResource(idDrawable);
                            break;
                        case 2:
                            jugador3TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j3.setImageResource(idDrawable);
                            break;
                        case 3:
                            jugador4TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j4.setImageResource(idDrawable);
                            break;
                        case 4:
                            jugador5TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j5.setImageResource(idDrawable);
                            break;
                        case 5:
                            jugador6TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j6.setImageResource(idDrawable);
                            break;
                        case 6:
                            jugador7TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j7.setImageResource(idDrawable);
                            break;
                        case 7:
                            jugador8TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j8.setImageResource(idDrawable);
                            break;
                        case 8:
                            jugador9TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j9.setImageResource(idDrawable);
                            break;
                        case 9:
                            jugador10TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j10.setImageResource(idDrawable);
                            break;
                        case 10:
                            jugador11TextView.setText(jugador);
                            jugadorLow = jugador.toLowerCase();
                            idDrawable = getResources().getIdentifier(jugadorLow, "drawable", requireActivity().getPackageName());
                            j11.setImageResource(idDrawable);
                            break;
                        default:
                            break;
                    }
                }
                String equipoDeseado = "Deportes Tolima";

                ArrayList<Jugador> jugadoresEquipo = new ArrayList<>();
                for (Jugador jugador : MainActivity.listaJugadores) {
                    if (jugador.getEquipo().equals(equipoDeseado)) {
                        jugadoresEquipo.add(jugador);
                    }
                }

                for (int i = 0; i < jugadoresEquipo.size(); i++) {
                    Jugador jugador = jugadoresEquipo.get(i);
                    switch (i) {
                        case 0:
                            jugador1Definido.setText(jugador.getNombre());
                            break;
                        case 1:
                            jugador2Definido.setText(jugador.getNombre());
                            break;
                        case 2:
                            jugador3Definido.setText(jugador.getNombre());
                            break;
                        case 3:
                            jugador4Definido.setText(jugador.getNombre());
                            break;
                        case 4:
                            jugador5Definido.setText(jugador.getNombre());
                            break;
                        case 5:
                            jugador6Definido.setText(jugador.getNombre());
                            break;
                        case 6:
                            jugador7Definido.setText(jugador.getNombre());
                            break;
                        case 7:
                            jugador8Definido.setText(jugador.getNombre());
                            break;
                        case 8:
                            jugador9Definido.setText(jugador.getNombre());
                            break;
                        case 9:
                            jugador10Definido.setText(jugador.getNombre());
                            break;
                        case 10:
                            jugador11Definido.setText(jugador.getNombre());
                            break;
                        default:
                            break;
                    }
                    TextView marcadorElegido = binding.MarcadorElegido;
                    TextView marcadorEquipo = binding.MarcadorEquipo;

                    int marcadorAleatorio = (int) (Math.random() * 10);
                    switch (i) {
                        case 0:
                            marcadorElegido.setText(String.valueOf(marcadorAleatorio));
                            break;
                        case 1:
                            marcadorEquipo.setText(String.valueOf(marcadorAleatorio));
                            break;
                        default:
                            break;
                    }
                }


            }
        });

        return root;
}

    private void actualizarImagenLocal(String equipo, ImageView equipoLocal) {
        if (equipo != null) {
            switch (equipo) {
                case "Nacional":
                    equipoLocal.setImageResource(R.drawable.nacional);
                    break;
                case "Tolima":
                    equipoLocal.setImageResource(R.drawable.tolima);
                    break;
                case "Cali":
                    equipoLocal.setImageResource(R.drawable.deporcali);
                    break;
                case "Once Caldas":
                    equipoLocal.setImageResource(R.drawable.caldas);
                    break;
                case "Junior":
                    equipoLocal.setImageResource(R.drawable.juniorfc);
                    break;
                case "Bucaramanga":
                    equipoLocal.setImageResource(R.drawable.bucaramanga);
                    break;
                case "Santa Fe":
                    equipoLocal.setImageResource(R.drawable.isantafe);
                    break;
                case "Equidad":
                    equipoLocal.setImageResource(R.drawable.laequidad);
                    break;
                case "Pereira":
                    equipoLocal.setImageResource(R.drawable.pereira);
                    break;
                default:
                    break;
            }
        } else {
            Log.i("hola: ",equipo);
        }
    }

}