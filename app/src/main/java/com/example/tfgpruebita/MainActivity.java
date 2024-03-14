package com.example.tfgpruebita;

import android.os.Bundle;

import com.example.tfgpruebita.modelo.Jugador;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tfgpruebita.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static ArrayList<String> lista = new ArrayList<>();
    public static ArrayList<Jugador> listaJugadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_equipo)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        listaJugadores.add(new Jugador("Gil", "Deportes Tolima", 2, 3, "Lesionado", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Hurtado", "Deportes Tolima", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Neto", "Deportes Tolima", 0, 0, "Activo", false, "Portero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Boné", "Deportes Tolima", 1, 0, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Guzman", "Deportes Tolima", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Fabian", "Deportes Tolima", 0, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Rovira", "Deportes Tolima", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Junior", "Deportes Tolima", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Angulo", "Deportes Tolima", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Quinones", "Deportes Tolima", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Messi", "Deportes Tolima", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Figueroa", "CD Jaguares", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Quintero", "Deportivo Pereira", 3, 5, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Jordan", "Deportivo Pereira", 0, 3, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Ibarguen", "Deportivo Pereira", 0, 1, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Faber", "Deportivo Pereira", 2, 2, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Murillo", "Deportivo Pereira", 1, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Rios", "Deportivo Pereira", 0, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Giraldo", "Deportivo Pereira", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Pestana", "Deportivo Pereira", 0, 0, "Activo", false, "Defensa", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Garces", "Deportivo Pereira", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Moreno", "Deportivo Pereira", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Ichazo", "Deportivo Pereira", 0, 0, "Activo", false, "Portero", "Medio", 0, 0));

        listaJugadores.add(new Jugador("Rojas", "La Equidad", 1, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Viveros", "La Equidad", 0, 4, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Torralvo", "La Equidad", 1, 0, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Ceballos", "La Equidad", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Acosta", "La Equidad", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Ricardo", "La Equidad", 0, 3, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Correa", "La Equidad", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Payares", "La Equidad", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Polanco", "La Equidad", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Viafara", "La Equidad", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Ortega", "La Equidad", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Rodriguez", "Santa Fe", 0, 1, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Rodallega", "Santa Fe", 0, 6, "Activo", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Torres", "Santa Fe", 1, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Velasquez", "Santa Fe", 0, 1, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Zuluaga", "Santa Fe", 0, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Chaverra", "Santa Fe", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Milan", "Santa Fe", 0, 2, "Activo", false, "Defensa", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Aguero", "Santa Fe", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Ortiz", "Santa Fe", 2, 0, "Activo", false, "Defensa", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Melendez", "Santa Fe", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Mosquera", "Santa Fe", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Daniel", "Bucaramanga", 1, 3, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Hinestroza", "Bucaramanga", 0, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Sambueza", "Bucaramanga", 0, 0, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Cordoba", "Bucaramanga", 0, 1, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Castro", "Bucaramanga", 1, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Flores", "Bucaramanga", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Juan", "Bucaramanga", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Henao", "Bucaramanga", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Gutierrez", "Bucaramanga", 0, 0, "Activo", false, "Defensa", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Quintana", "Bucaramanga", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Bacca", "Junior", 1, 3, "Activo", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Caicedo", "Junior", 1, 1, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Enamorado", "Junior", 3, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Martinez", "Junior", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Didier", "Junior", 0, 2, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Fuentes", "Junior", 0, 1, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Pena", "Junior", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Olivera", "Junior", 0, 0, "Activo", false, "Defensa", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Gonzalez", "Junior", 2, 1, "Activo", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Pacheco", "Junior", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Mele", "Junior", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Dayro", "Once Caldas", 1, 6, "Activo", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Lemos", "Once Caldas", 0, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Beltran", "Once Caldas", 0, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Gustavo", "Once Caldas", 0, 1, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Montano", "Once Caldas", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Garcia", "Once Caldas", 0, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Juanca", "Once Caldas", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Riquett", "Once Caldas", 1, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Palacios", "Once Caldas", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Cuesta", "Once Caldas", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Aguirre", "Once Caldas", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Camargo", "Deportivo Cali", 1, 1, "Activo", false, "Delantero", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Castillo", "Deportivo Cali", 1, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Reina", "Deportivo Cali", 2, 2, "Activo", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("JuanCordoba", "Deportivo Cali", 2, 6, "Activo", false, "Delantero", "Alto", 0, 0));
        listaJugadores.add(new Jugador("Osorio", "Deportivo Cali", 1, 1, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Mejia", "Deportivo Cali", 0, 0, "Activo", false, "Mediocentro", "Medio", 0, 0));
        listaJugadores.add(new Jugador("Gomez", "Deportivo Cali", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Brayan", "Deportivo Cali", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Meza", "Deportivo Cali", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Marulanda", "Deportivo Cali", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Caldera", "Deportivo Cali", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));

        listaJugadores.add(new Jugador("Quintero", "Atlético Nacional", 1, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Barrero", "Atlético Nacional", 0, 0, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Duque", "Atlético Nacional", 0, 1, "Activo", false, "Delantero", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Aldair", "Atlético Nacional", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Gomez", "Atlético Nacional", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Valencia", "Atlético Nacional", 0, 0, "Activo", false, "Mediocentro", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Fabra", "Atlético Nacional", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Aguilar", "Atlético Nacional", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Moya", "Atlético Nacional", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Hernandez", "Atlético Nacional", 0, 0, "Activo", false, "Defensa", "Bajo", 0, 0));
        listaJugadores.add(new Jugador("Monetti", "Atlético Nacional", 0, 0, "Activo", false, "Portero", "Bajo", 0, 0));}

}