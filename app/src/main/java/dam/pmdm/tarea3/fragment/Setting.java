package dam.pmdm.tarea3.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

import dam.pmdm.tarea3.R;


public class Setting extends Fragment {

    public Setting() {
        // Required empty public constructor
    }



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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Button btn = (Button) view.findViewById(R.id.Closed);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Switch sw = (Switch) view.findViewById(R.id.eliPok);
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

        return view;
    }

}