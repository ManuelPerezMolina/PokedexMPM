package dam.pmdm.tarea3.fragment;

import static dam.pmdm.tarea3.Global.setFragment;
import static dam.pmdm.tarea3.Global.setPokemonCapturados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.bd.CompletarDatos;
import dam.pmdm.tarea3.rv.PokemonAdapter;
import dam.pmdm.tarea3.rv.PokemonData;


public class PokemonCapturados extends Fragment {

    private RecyclerView tlp;
    private PokemonAdapter adapter;
    private Context context;
    public static final String TAG = "POKEDEX";
    List<PokemonData> listaPokedex = new ArrayList<PokemonData>();
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pokemon_capturados, container, false);
        tlp= (RecyclerView) vista.findViewById(R.id.amRvpersonajes);
        tlp.setLayoutManager(new LinearLayoutManager(context));
        List<PokemonData> lp = showPokemonCapturados(listaPokedex);
        adapter = new PokemonAdapter(lp,getContext());
        tlp.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itc);
        itemTouchHelper.attachToRecyclerView(tlp);
        return vista;
    }

    public List<PokemonData> showPokemonCapturados(List<PokemonData> listaPokedex) {

        ArrayList<String> pokemonCapturados = new ArrayList();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Capturados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Object dato = document.getData().get("email");
                                auth = FirebaseAuth.getInstance();
                                String email = auth.getCurrentUser().getEmail();
                                if (dato.toString().equals(email)) {
                                    Object dato1 = document.getData().get("id");
                                    Object dato2 = document.getData().get("name");
                                    Object dato3 = "/" + dato1;
                                    listaPokedex.add(new PokemonData(dato1.toString(), dato2.toString(), dato3.toString()));
                                    setPokemonCapturados(dato1.toString(),false);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return listaPokedex;
    }

    ItemTouchHelper.SimpleCallback itc = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }



        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            tlp.removeViewAt(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            adapter.notifyDataSetChanged();
            SharedPreferences prefe = getActivity().getSharedPreferences("eliminar", 0);
            if (prefe.getBoolean("eliminar",false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.elegir_opcion)
                        .setTitle(R.string.opcion_title);
                builder.setPositiveButton(R.string.borrar, (dialog, id) -> {
                    CompletarDatos c = new CompletarDatos(listaPokedex.get(position).getNumero());
                    setPokemonCapturados(listaPokedex.get(position).getNumero(),true);
                    listaPokedex.remove(position);
                    adapter.notifyDataSetChanged();
                });
                builder.setNegativeButton(R.string.cancelar, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Eliminar pokemon");
                builder.setMessage(R.string.opcion_eliminar);
                builder.setPositiveButton("Aceptar", null);
                builder.show();
            }


        }
    };


}