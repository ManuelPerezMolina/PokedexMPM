package dam.pmdm.tarea3.rv;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dam.pmdm.tarea3.databinding.PokemonCardviewBinding;

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