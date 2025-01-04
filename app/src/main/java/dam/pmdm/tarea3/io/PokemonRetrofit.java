package dam.pmdm.tarea3.io;

import java.util.ArrayList;

import dam.pmdm.tarea3.rv.PokemonData;

public class PokemonRetrofit {

    private ArrayList<PokemonData> results;

    public ArrayList<PokemonData> getResults() {
        return results;
    }

    void setResults(ArrayList<PokemonData> results) {
        this.results = results;
    }
}
