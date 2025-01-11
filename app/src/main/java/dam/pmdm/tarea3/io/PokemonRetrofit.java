package dam.pmdm.tarea3.io;

import java.util.ArrayList;

import dam.pmdm.tarea3.bd.PokemonBd;
import dam.pmdm.tarea3.rv.PokemonData;

public class PokemonRetrofit {

    private ArrayList<PokemonData> results;
    private PokemonBd datos;

    public ArrayList<PokemonData> getResults() {
        return results;
    }

    public PokemonBd getDatos() {
        return datos;
    }
}
