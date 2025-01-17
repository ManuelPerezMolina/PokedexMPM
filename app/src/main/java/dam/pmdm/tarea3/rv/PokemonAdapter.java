package dam.pmdm.tarea3.rv;

import static android.app.ProgressDialog.show;
import static dam.pmdm.tarea3.Global.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dam.pmdm.tarea3.Global;
import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.fragment.Pokedex;
import dam.pmdm.tarea3.fragment.PokemonCapturados;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

     private ArrayList<String> pokemoCapturados;
    private List<PokemonData> items;
    private Context context;

    public PokemonAdapter(List<PokemonData> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_cardview, parent, false);
       pokemoCapturados = Global.getPokemonCapturados();

       return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PokemonData p = items.get(position);
        holder.nombre.setText(p.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumero() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagen);

       if (pokemoCapturados != null & (fragment instanceof Pokedex)){
           if (pokemoCapturados.contains(p.getNumero())) {
               holder.itemView.findViewById(R.id.pcCapturado).setVisibility(View.VISIBLE);
               holder.itemView.findViewById(R.id.pccardview).setActivated(false);
               holder.itemView.findViewById(R.id.pccardview).setBackgroundColor(R.color.rojo);
           }
       }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((fragment instanceof Pokedex)) {
                    Pokedex.pokemonClicked(items.get(position), v);
                    v.setBackgroundColor(R.color.rojo);
                    v.setClickable(false);
                    v.findViewById(R.id.pcCapturado).setVisibility(View.VISIBLE);
                } else if (fragment instanceof PokemonCapturados) {
                    Toast.makeText(v.getContext(), "Pokemon: " + items.get(position).getName(), Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagen;
        private TextView nombre;
        private TextView tipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.pcimagenview);
            nombre = itemView.findViewById(R.id.pctextview);
        }
    }
}
