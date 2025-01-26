package dam.pmdm.tarea3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import dam.pmdm.tarea3.Global;
import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.bd.CompletarDatos;
import dam.pmdm.tarea3.listTipos.Tipos;
import dam.pmdm.tarea3.rv.PokemonData;


public class Detalles extends Fragment {

    public PokemonData pk;
    private ImageView imagen;
    private TextView nombre;
    private TextView altura;
    private TextView peso;
    private ListView tipos;
    private CompletarDatos c;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                (fragmentManager).beginTransaction()
                        .replace(R.id.my_nav_host_fragment, PokemonCapturados.class, null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        pk = Global.getPokemonSeleccionado();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalles, container, false);
        cargarDatos();

        return vista;
    }


    private void cargarDatos() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        System.out.println(pk.getName());
        fb.collection("Capturados").document(pk.getName()).get().addOnSuccessListener(documentSnapshot -> {
            nombre = getView().findViewById(R.id.ivNombre);
            nombre.setText(documentSnapshot.get("name").toString());

            imagen = getView().findViewById(R.id.ivDetalles);
            String tnumero = documentSnapshot.get("id").toString();
            Glide.with(this)
                    .load(documentSnapshot.get("url").toString())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagen);

            altura = getView().findViewById(R.id.altura);
            peso = getView().findViewById(R.id.peso);


            String Taltura = String.format("%2$,3.2f %1$s"," m",(documentSnapshot.get("height")));
            altura.setText(Taltura);
            String Tpeso = String.format("%2$,3.2f %1$s"," kg",(documentSnapshot.get("weight")));
            peso.setText(Tpeso);
            tipos = getView().findViewById(R.id.lvTipos);
            GenericTypeIndicator<List<Tipos>> typeIndicator = new GenericTypeIndicator<List<Tipos>>() {};
            List<Tipos> tiposbd = List.class.cast(documentSnapshot.get("types"));
            ArrayAdapter<Tipos> adapter = new ArrayAdapter<>(this.getContext(), R.layout.list_item_tipos, tiposbd);
            tipos.setAdapter(adapter);
        });
    }
}

