package dam.pmdm.tarea3.fragment;

import static dam.pmdm.tarea3.Global.pokemonCapturados;
import static dam.pmdm.tarea3.Global.setFragment;
import static dam.pmdm.tarea3.Global.setPokemonCapturados;
import static dam.pmdm.tarea3.fragment.Pokedex.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmRights;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dam.pmdm.tarea3.Global;
import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.bd.CompletarDatos;
import dam.pmdm.tarea3.bd.GestionBD;
import dam.pmdm.tarea3.rv.PokemonAdapter;
import dam.pmdm.tarea3.rv.PokemonData;
import okhttp3.internal.cache.DiskLruCache;


public class PokemonCapturados extends Fragment {

    private RecyclerView tlp;
    private PokemonAdapter adapter;
    private Context context;
    public static final String TAG = "POKEDEX";
    public ArrayList<String>  listaCapturados = null;
    List<PokemonData> listaPokedex = new ArrayList<PokemonData>();

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
        setPokemonCapturados("");
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
                                Object dato1 = document.getData().get("id");
                                Object dato2 = document.getData().get("name");
                                Object dato3 = "/" +dato1;
                                listaPokedex.add(new PokemonData(dato1.toString(), dato2.toString(), dato3.toString()));
                                setPokemonCapturados(dato1.toString());
                                adapter.notifyDataSetChanged();
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
            System.out.println(listaPokedex.get(position).getNumero());
            CompletarDatos c = new CompletarDatos(listaPokedex.get(position).getNumero());
        }
    };
}