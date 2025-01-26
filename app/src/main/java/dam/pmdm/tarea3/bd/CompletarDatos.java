package dam.pmdm.tarea3.bd;

import static dam.pmdm.tarea3.fragment.Pokedex.TAG;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dam.pmdm.tarea3.io.PokemonInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompletarDatos {

    private PokemonInterface pokemonApi2;

    public CompletarDatos(String numero) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pokemonApi2 = retrofit.create(PokemonInterface.class);

        Call<PokemonBd> pokemonRetrofitCall = pokemonApi2.obtenerPokemon(numero);
        pokemonRetrofitCall.enqueue(new Callback<PokemonBd>() {

            @Override
            public void onResponse(Call<PokemonBd> call, Response<PokemonBd> response) {
                if (response.isSuccessful()) {
                    PokemonBd pokemonRespuesta = response.body();
                    ObtenerDatos(pokemonRespuesta);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonBd> call, Throwable throwable) {

            }
        });
    }

    public void ObtenerDatos(PokemonBd datos) {

        JSONArray array = new JSONArray(datos.getTypes());
        ArrayList<String> tipos = new ArrayList<>();
        try {
            JSONObject obj2 = null;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                obj2 = (JSONObject) obj.get("type");
                tipos.add(obj2.get("name").toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String URLimagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/" + datos.getId() + ".png";
        ArrayList datosBD = new ArrayList();
        datosBD.add(datos.getName());
        datosBD.add(datos.getId());
        datosBD.add(URLimagen);
        datosBD.add(tipos);
        datosBD.add(datos.getHeight()/10);
        datosBD.add(datos.getWeight()/10);
        new GestionBD(datosBD);
    }
}
