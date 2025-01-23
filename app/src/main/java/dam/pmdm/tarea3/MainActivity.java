package dam.pmdm.tarea3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import dam.pmdm.tarea3.fragment.Pokedex;
import dam.pmdm.tarea3.fragment.PokemonCapturados;
import dam.pmdm.tarea3.fragment.Setting;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private String idioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.pokemonCapturados);
        idioma=getSharedPreferences("idioma", 0).getString("idioma", "es");
        if (idioma.equals("es")) {
            Locale loc = new Locale("es");
            Locale.setDefault(loc);
            Configuration config = new Configuration();
            config.setLocale(loc);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }else{
            Locale loc = new Locale("en");
            Locale.setDefault(loc);
            Configuration config = new Configuration();
            config.setLocale(loc);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }

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