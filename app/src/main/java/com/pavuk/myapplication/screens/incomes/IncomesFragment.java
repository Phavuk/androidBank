package com.pavuk.myapplication.screens.incomes;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavuk.myapplication.MainActivity;
import com.pavuk.myapplication.databinding.IncomesFragmentBinding;
import com.pavuk.myapplication.db.SavingsDatabase;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;
import com.pavuk.myapplication.screens.charges.ChargesViewModel;

import java.util.ArrayList;
import java.util.List;

public class IncomesFragment extends Fragment {

    private IncomesViewModel mViewModel;
    private IncomesFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SavingsDatabase db = ((MainActivity)getActivity()).database;
        binding = IncomesFragmentBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this, new IncomesViewModel.FactoryIncomesModel(db)).get(IncomesViewModel.class);

        ((MainActivity) getActivity()).setToolbarTitle("Incomes");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final IncomesAdapter adapter = new IncomesAdapter();

        binding.recyclerView.setAdapter(adapter);

        mViewModel.getAllIncomes().observe(getViewLifecycleOwner(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                adapter.submitList(incomes);
            }
        });

        return binding.getRoot();
    }
}