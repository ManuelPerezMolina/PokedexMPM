package dam.pmdm.tarea3.rv;


import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static dam.pmdm.tarea3.Global.fragment;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dam.pmdm.tarea3.databinding.PokemonCardviewBinding;
import dam.pmdm.tarea3.fragment.Pokedex;

public class PokemonViewHolder extends RecyclerView.ViewHolder{

    private PokemonCardviewBinding binding;


    public PokemonViewHolder(PokemonCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;


    }

    public void bind(PokemonData datapokemon){

        binding.pctextview.setText(datapokemon.getName());
        Glide.with(binding.getRoot());
    }

}