package dam.pmdm.tarea3.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import dam.pmdm.tarea3.R;


public class Setting extends Fragment {

    private String idioma;
    private Button bt1,bt2,btn;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((androidx.fragment.app.FragmentManager) fragmentManager).beginTransaction()
                        .replace(R.id.my_nav_host_fragment, PokemonCapturados.class, null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btn = view.findViewById(R.id.Closed);
        bt1 = view.findViewById(R.id.English);
        bt2 = view.findViewById(R.id.Castellano);
        idioma=getActivity().getSharedPreferences("idioma", 0).getString("idioma", "es");
        if (idioma.equals("es")) {
            bt2.setEnabled(false);
            bt2.setAlpha(.5f);
        } else {
            bt1.setEnabled(false);
            bt1.setAlpha(.5f);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})

        SwitchCompat sw = view.findViewById(R.id.eliPok);
        sw.setChecked(getActivity().getSharedPreferences("eliminar", 0).getBoolean("eliminar", false));
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw.isChecked()) {
                    SharedPreferences elipok = getActivity().getSharedPreferences("eliminar", 0);
                    SharedPreferences.Editor editor = elipok.edit();
                    editor.putBoolean("eliminar", true);
                    editor.commit();
                } else {
                    SharedPreferences elipok = getActivity().getSharedPreferences("eliminar", 0);
                    SharedPreferences.Editor editor = elipok.edit();
                    editor.putBoolean("eliminar", false);
                    editor.commit();
                }
            }
        });

        bt1.setOnClickListener(v -> {
            Locale loc = new Locale("en");
            Locale.setDefault(loc);
            Configuration config = new Configuration();
            config.setLocale(loc);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            getActivity().recreate();
            SharedPreferences preferencias= getActivity().getSharedPreferences("idioma", 0);
            SharedPreferences.Editor editor=preferencias.edit();
            editor.putString("idioma", "en");
            editor.commit();
            bt2.setBackgroundColor(R.color.rojo);
            bt1.setActivated(false);
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale loc = new Locale("es");
                Locale.setDefault(loc);
                Configuration config = new Configuration();
                config.setLocale(loc);
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                getActivity().recreate();
                SharedPreferences preferencias= getActivity().getSharedPreferences("idioma", 0);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("idioma", "es");
                editor.commit();
                bt1.setBackgroundColor(R.color.rojo);
                bt2.setActivated(false);
            }
        });
        return view;
    }

}