package dam.pmdm.tarea3;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import dam.pmdm.tarea3.rv.PokemonData;

public class Global {

    public static  ArrayList<String> pokemonCapturados = new ArrayList();
    public static Fragment fragment;
    public static PokemonData pokemonSeleccionado;

    public static void setPokemonCapturados(String pc,boolean borrado) {
        if (borrado) {
            pokemonCapturados.remove(pc);
        } else {
            if (!pokemonCapturados.contains(pc)){
                pokemonCapturados.add(pc);
            }
        }

        Global.pokemonCapturados = pokemonCapturados;
    }

    public static ArrayList getPokemonCapturados() {
        return pokemonCapturados;
    }

    public static void setFragment(Fragment fragment) {
        Global.fragment = fragment;
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static PokemonData getPokemonSeleccionado() {
        return pokemonSeleccionado;
    }

    public static void setPokemonSeleccionado(PokemonData pokemonSeleccionado) {
        Global.pokemonSeleccionado = pokemonSeleccionado;
    }

}
