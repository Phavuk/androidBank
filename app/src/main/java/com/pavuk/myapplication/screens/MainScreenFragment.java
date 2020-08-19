package com.pavuk.myapplication.screens;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.data.Set;
import com.pavuk.myapplication.MainActivity;
import com.pavuk.myapplication.R;
import com.pavuk.myapplication.databinding.MainScreenFragmentBinding;
import com.pavuk.myapplication.db.SavingsDatabase;
import com.pavuk.myapplication.db.entity.CategoryPrice;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;
import com.pavuk.myapplication.dialog.AddDialog;
import com.pavuk.myapplication.dialog.AddDialogListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainScreenFragment
        extends Fragment {

    private Pie pie;
    private MainScreenViewModel mViewModel;
    private MainScreenFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /** getnutie jednej inštancie databázy */
        SavingsDatabase db = ((MainActivity)getActivity()).database;

        /** seetnutie bindingu */
        binding = MainScreenFragmentBinding.inflate(inflater, container, false);

        /** inicializovanie viewmodelu pre MainScreenFragment*/
        mViewModel = new ViewModelProvider(this, new MainScreenViewModel.Factory(db)).get(MainScreenViewModel.class);

        /** setneme meno, title v toolbare */
        ((MainActivity) getActivity()).setToolbarTitle("Spending report");

        /** vytvorenie inštancie grafu chart pie*/
        pie = AnyChart.pie();
        /** setneme pie do layoutu */
        binding.chartView.setChart(pie);

        /** observer pre celkovu sumu výdajov */
        mViewModel.getChargeSum().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(aDouble != null){
                    binding.chargesSumText.setText("- " + String.valueOf(aDouble) + " €");
                } else {
                    binding.chargesSumText.setText("0"+ " €");
                }
            }
        });

        /** observer pre celkovu sumu príjmov */
        mViewModel.getIncomeSum().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(aDouble != null) {
                    binding.incomeSumText.setText(String.valueOf(aDouble) + " €");
                }else {
                    binding.incomeSumText.setText("0"+ " €");
                }
            }
        });

        try {
            /** observer pre graf */
            mViewModel.getNamePrice().observe(getViewLifecycleOwner(), new Observer<List<CategoryPrice>>() {
                List<DataEntry> data;

                @Override
                public void onChanged(List<CategoryPrice> categoryPrices) {
                    data = new ArrayList<>();
                    if (categoryPrices.isEmpty()) {
                        data.clear();
                        data.add(new ValueDataEntry("Empty", 1));
                        pie.data(data);
                    } else {
                        data.clear();
                        for (CategoryPrice valToChart : categoryPrices) {
                            Log.i("Val", "onChanged: " + valToChart.category + " " + valToChart.price);
                            data.add(new ValueDataEntry(valToChart.category, valToChart.price));
                        }
                        pie.data(data);
                    }
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /** Charge Dialog Callback */
        final AddDialogListener listenerCharge = new AddDialogListener() {
            @Override
            public void onPositiveClick(String[] arr) {
                Charge chargeToDb = new Charge(arr[0], Double.valueOf(arr[1]), arr[2]);
                mViewModel.insertCharge(chargeToDb);
            }
        };

        binding.chargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDialog(listenerCharge, "Add charge").show(getActivity().getSupportFragmentManager(), "test");
            }
        });


        /** Income Dialog Callback */
        final AddDialogListener listenerIncome = new AddDialogListener() {
            @Override
            public void onPositiveClick(String[] arr) {
                Toast.makeText(requireContext(), arr[0] + " " + arr[1] + " " + arr[2], Toast.LENGTH_SHORT).show();
                Income incomeToDb = new Income(arr[0], Double.valueOf(arr[1]), arr[2]);
                mViewModel.insertIncome(incomeToDb);
            }
        };

        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDialog(listenerIncome, "Add income").show(getActivity().getSupportFragmentManager(), "test");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        /** bez toho nevytvorí horné menu s jednou ikonkou v toolbare */
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        /** setnutie menu v toolbare*/
        inflater.inflate(R.menu.options_menu_appbar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        /** switch pre to aby sme vedeli na ktory button sme klikni v toolbare */
        switch(id){
            case R.id.deleteAllRecords: {
                //Toast.makeText(requireContext(), "delete all test", Toast.LENGTH_SHORT).show();
                /** vymazanie všetkých záznamov z databázy */
                mViewModel.deleteAllRecords();
                Toast.makeText(requireContext(), "All records were deleted", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}