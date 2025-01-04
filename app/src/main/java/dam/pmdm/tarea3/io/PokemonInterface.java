package dam.pmdm.tarea3.io;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface PokemonInterface {

    @GET("pokemon")
    Call<PokemonRetrofit> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}/")
    Call<PokemonRetrofit> obtenerPokemon(@Query("id") int id);
}
