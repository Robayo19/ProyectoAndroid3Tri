package com.example.tfgpruebita.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tfgpruebita.R;
import com.example.tfgpruebita.databinding.FragmentHomeBinding;
import com.example.tfgpruebita.ui.LoginRegister.Principal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static String equipoPasar;

    private String correo;

    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WebView webView = root.findViewById(R.id.webView);
        webView.loadUrl("https://www.marca.com/");

        Button btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            correo = args.getString("correo");
        }
        textView = root.findViewById(R.id.textUserInfo);
        textView.setText(user.getEmail().toString());

        seleccionEquipo();

        return root;
    }

    public static HomeFragment newInstance(String correo) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("correo", correo);
        fragment.setArguments(args);
        return fragment;
    }

    private void seleccionEquipo() {
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String userId = user.getUid();
        String equipoSeleccionado = sharedPreferences.getString("equipo_favorito_" + userId, null);

        if (equipoSeleccionado != null) {
            equipoPasar = equipoSeleccionado;
            actualizarImageUserSegunEquipo(equipoSeleccionado);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Selecciona tu equipo favorito");
            String[] equipos = {"Nacional", "Tolima", "Cali", "Once Caldas", "Junior", "Bucaramanga", "Santa Fe", "Equidad", "Pereira"};

            builder.setSingleChoiceItems(equipos, -1, (dialog, which) -> {
                String equipo = equipos[which];
                actualizarImageUserSegunEquipo(equipo);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.i("equipo: ", equipo);
                equipoPasar = equipo;
                editor.putString("equipo_favorito_" + userId, equipo);
                editor.apply();

                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



    private void actualizarImageUserSegunEquipo(String equipoSeleccionado) {
        switch (equipoSeleccionado) {
            case "Nacional":
                binding.imageUser.setImageResource(R.drawable.nacional);
                break;
            case "Tolima":
                binding.imageUser.setImageResource(R.drawable.tolima);
                break;
            case "Cali":
                binding.imageUser.setImageResource(R.drawable.deporcali);
                break;
            case "Once Caldas":
                binding.imageUser.setImageResource(R.drawable.caldas);
                break;
            case "Junior":
                binding.imageUser.setImageResource(R.drawable.juniorfc);
                break;
            case "Bucaramanga":
                binding.imageUser.setImageResource(R.drawable.bucaramanga);
                break;
            case "Santa Fe":
                binding.imageUser.setImageResource(R.drawable.isantafe);
                break;
            case "Equidad":
                binding.imageUser.setImageResource(R.drawable.laequidad);
                break;
            case "Pereira":
                binding.imageUser.setImageResource(R.drawable.pereira);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
