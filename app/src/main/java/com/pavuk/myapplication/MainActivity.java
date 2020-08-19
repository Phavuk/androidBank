package com.pavuk.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.pavuk.myapplication.databinding.ActivityMainBinding;
import com.pavuk.myapplication.databinding.MainScreenFragmentBinding;
import com.pavuk.myapplication.db.SavingsDatabase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    public SavingsDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** nastavenie bindingu pre main activitu */
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        /** getnutie naVcontrolleru */
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        /** setnutie toolbaru */
        setSupportActionBar(binding.myToolbar);

        /** setnutie navigacie s nasim controllerom */
        NavigationUI.setupWithNavController(binding.bottomNav,navController);

        /** getnutie instancie databazy */
        database = SavingsDatabase.getInstance(this);

        /** nastavenie title - dame tam nič */
        getSupportActionBar().setTitle("");
    }

    /** metoda pre fragmenty pre setovanie mena,title v appbare */
    public void setToolbarTitle(String toolbarTitle){
        binding.toolbarTitle.setText(toolbarTitle);
    }
}