package dam.pmdm.tarea3.bd;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static dam.pmdm.tarea3.Global.fragment;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dam.pmdm.tarea3.R;
import dam.pmdm.tarea3.fragment.Pokedex;
import dam.pmdm.tarea3.fragment.PokemonCapturados;

public class GestionBD {

    @SuppressLint("RestrictedApi")
    public GestionBD(ArrayList pokemondatos){

        String nombre = pokemondatos.get(0).toString();
        String id = pokemondatos.get(1).toString();
        String imagen = pokemondatos.get(2).toString();
        ArrayList tipos = (ArrayList) pokemondatos.get(3);
        Float altura = (Float) pokemondatos.get(4);
        Float peso = (Float) pokemondatos.get(5);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        PokemonBd pcapturados = new PokemonBd(tipos,altura,peso,imagen,nombre,id,email) ;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (fragment instanceof Pokedex){
            db.collection("Capturados").document(nombre).set(pcapturados)
                    .addOnSuccessListener(runnable ->
                            Toast.makeText(getApplicationContext(), R.string.capturado, Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(runnable ->
                            Toast.makeText(getApplicationContext(),R.string.sin_capturar, Toast.LENGTH_SHORT).show());
        }

        if (fragment instanceof PokemonCapturados){
            db.collection("Capturados").document(nombre).delete();
        }



    }


}
