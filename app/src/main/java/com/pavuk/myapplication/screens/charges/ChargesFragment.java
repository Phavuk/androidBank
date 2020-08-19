package com.pavuk.myapplication.screens.charges;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavuk.myapplication.MainActivity;
import com.pavuk.myapplication.databinding.ChargesFragmentBinding;
import com.pavuk.myapplication.db.SavingsDatabase;
import com.pavuk.myapplication.db.entity.Charge;

import java.util.List;

public class ChargesFragment extends Fragment {

    private ChargesViewModel mViewModel;
    private ChargesFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /** getneme databazu z mainactivity */
        SavingsDatabase db = ((MainActivity)getActivity()).database;

        /** setneme binding */
        binding = ChargesFragmentBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this, new ChargesViewModel.FactoryChargesModel(db)).get(ChargesViewModel.class);

        /** setneme meno, title v toolbare */
        ((MainActivity) getActivity()).setToolbarTitle("Charges");

        /** setneme layout manager pre recyclerview */
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /** inicializacia adapteru pre recyclerview - cez to budeme submitovat nový list */
        final ChargesAdapter adapter = new ChargesAdapter();

        /** setneme adapter pre recyclerview */
        binding.recyclerView.setAdapter(adapter);

        /** observer ktorý čaka na zmenu dát z db */
        mViewModel.getAllCharges().observe(getViewLifecycleOwner(), new Observer<List<Charge>>() {
            @Override
            public void onChanged(List<Charge> charges) {
                /** zmena prišla uložíme nový list do adaptera pre recycler */
                adapter.submitList(charges);
            }
        });

        return binding.getRoot();
    }
}