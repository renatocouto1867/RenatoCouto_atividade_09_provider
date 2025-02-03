package com.example.renatocouto_atividade_09_provider;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.renatocouto_atividade_09_provider.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_cadastrar, R.id.navigation_listar)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        configuraBotaoSair();

    } // onCreate

    /**
     * Usei esse metodo para captura o clique no menu sair, e depois eu retorno o navController para
     * a navegação voltar a funcionar pelo Navigation, sem o
     * return NavigationUI.onNavDestinationSelected(itemMenu, navController), o menu para de funcionar
     */
    private void configuraBotaoSair() {
        binding.navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_sair) {
                finish();
                return true;
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
    }

}