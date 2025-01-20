package dam.pmdm.tarea3;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import dam.pmdm.tarea3.fragment.Pokedex;
import dam.pmdm.tarea3.fragment.PokemonCapturados;
import dam.pmdm.tarea3.fragment.Setting;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private FirebaseFirestoreSettings mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.pokemonCapturados);

}


    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (itemId == R.id.pokemonCapturados) {
           fragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, PokemonCapturados.class, null)
                .commit();
            return true;
        } else if (itemId == R.id.pokedex) {
           fragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, Pokedex.class, null)
                .commit();
            return true;
        } else if (itemId == R.id.setting) {
            fragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, Setting.class, null)
                .commit();
            return true;
        }
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}