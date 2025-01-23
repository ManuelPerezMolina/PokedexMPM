package dam.pmdm.tarea3.fragment;

import static dam.pmdm.tarea3.Global.setFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.bd.CompletarDatos;
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
    public static final String TAG = "POKEDEX";
    private PokemonInterface pokemonApi;
    List<PokemonData> listaPokedex = new ArrayList<>();
    private boolean cargado =false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                fragmentManager.beginTransaction()
                        .replace(R.id.my_nav_host_fragment, PokemonCapturados.class, null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        View vista = inflater.inflate(R.layout.fragment_pokedex, container, false);
//        tlp= vista.findViewById(R.id.todosLosPokemons);
//        tlp.setLayoutManager(new LinearLayoutManager(context));
//        List<PokemonData> lp = showPokemon(listaPokedex);
//        adapter = new PokemonAdapter(lp,getContext());
//        tlp.setAdapter(adapter);

       return vista;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = context;
        tlp= getView().findViewById(R.id.todosLosPokemons);
        tlp.setLayoutManager(new LinearLayoutManager(context));
        List<PokemonData> lp = showPokemon(listaPokedex);
        adapter = new PokemonAdapter(lp,getContext());
        tlp.setAdapter(adapter);

    }



    public List<PokemonData> showPokemon(List<PokemonData> listaPokedex) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pokemonApi = retrofit.create(PokemonInterface.class);

        Call<PokemonRetrofit> pokemonRetrofitCall = pokemonApi.obtenerListaPokemon(20,0);
        pokemonRetrofitCall.enqueue(new Callback<>() {

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

        Toast.makeText(view.getContext(), "Pokemon: " + currentPokemon.getName(), Toast.LENGTH_SHORT).show();
        CompletarDatos c = new CompletarDatos(currentPokemon.getNumero());
    }
}