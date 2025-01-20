package dam.pmdm.tarea3.rv;

import static android.app.PendingIntent.getActivity;
import static com.google.android.play.integrity.internal.al.b;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import dam.pmdm.tarea3.Global;
import dam.pmdm.tarea3.MainActivity;
import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.fragment.Detalles;
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
        System.out.println("Numero: " + p.getNumero());
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumero() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagen);

       if (pokemoCapturados != null & (fragment instanceof Pokedex)){
           if (pokemoCapturados.contains(p.getNumero())) {
               holder.itemView.findViewById(R.id.pcCapturado).setVisibility(View.VISIBLE);
               holder.itemView.findViewById(R.id.pccardview).setActivated(false);
               holder.itemView.findViewById(R.id.pclinearlayout).setBackground(null);
               holder.itemView.findViewById(R.id.pclinearlayout).setBackgroundColor(R.color.azul);
           } else {
               holder.itemView.findViewById(R.id.pcCapturado).setVisibility(View.INVISIBLE);
               holder.itemView.findViewById(R.id.pccardview).setActivated(true);
               holder.itemView.findViewById(R.id.pccardview).setBackground(null);
               holder.itemView.findViewById(R.id.pclinearlayout).setBackgroundColor(R.color.rojo);
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
                    Global.setPokemonSeleccionado(items.get(position));
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.my_nav_host_fragment, Detalles.class, null);
                    ft.commit();
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
