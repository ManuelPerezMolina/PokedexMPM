package dam.pmdm.tarea3.io;

import dam.pmdm.tarea3.bd.PokemonBd;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PokemonInterface {

    @GET("pokemon")
    Call<PokemonRetrofit> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}/")
    Call<PokemonBd> obtenerPokemon(@Path("id") String id);
}
