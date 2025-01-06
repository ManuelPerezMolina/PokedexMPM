package dam.pmdm.tarea3.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.io.PokemonInterface;
import dam.pmdm.tarea3.io.PokemonRetrofit;
import dam.pmdm.tarea3.rv.PokemonAdapter;
import dam.pmdm.tarea3.rv.PokemonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Pokedex extends Fragment {

    private RecyclerView tlp;
    private PokemonAdapter adapter;
    private Context context;
    private static final String TAG = "POKEDEX";
    private PokemonInterface pokemonApi;
    List<PokemonData> listaPokedex;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_pokedex, container, false);

        tlp= (RecyclerView) vista.findViewById(R.id.todosLosPokemons);
        tlp.setLayoutManager(new LinearLayoutManager(context));
        listaPokedex = new ArrayList<PokemonData>();
        List<PokemonData> lp = showPokemon(listaPokedex);
        adapter = new PokemonAdapter(lp,getContext());
        tlp.setAdapter(adapter);
        adapter.notifyDataSetChanged();

       return vista;
    }

    public List<PokemonData> showPokemon(List<PokemonData> listaPokedex) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pokemonApi = retrofit.create(PokemonInterface.class);

        Call<PokemonRetrofit> pokemonRetrofitCall = pokemonApi.obtenerListaPokemon(150,0);
        pokemonRetrofitCall.enqueue(new Callback<PokemonRetrofit>() {

            @Override
            public void onResponse(Call<PokemonRetrofit> call, Response<PokemonRetrofit> response) {
                if (response.isSuccessful()) {
                    PokemonRetrofit pokemonRespuesta = response.body();
                    ArrayList<PokemonData> listaPoke = pokemonRespuesta.getResults();
                    listaPokedex.addAll(listaPoke);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<PokemonRetrofit> call, Throwable throwable) {
            }
        });
        return  listaPokedex;
    }

    public static void pokemonClicked(PokemonData currentPokemon, View view) {



    }
}