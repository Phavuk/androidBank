package com.pavuk.myapplication.screens.charges;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pavuk.myapplication.db.Dao.ChargeDao;
import com.pavuk.myapplication.db.Dao.IncomeDao;
import com.pavuk.myapplication.db.SavingsDatabase;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;
import com.pavuk.myapplication.screens.MainScreenViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChargesViewModel extends ViewModel {

    private SavingsDatabase db;

    /** constructor */
    public ChargesViewModel(SavingsDatabase db) {
        this.db = db;
    }

    /** livedata to observe charges list from db */
    LiveData<List<Charge>> getAllCharges() {
        return db.chargeDao().loadAllCharges();
    }

    /** factory pre vytvorenie viewmodelu s parametrom */
    static class FactoryChargesModel implements ViewModelProvider.Factory {
        private SavingsDatabase mDb;

        public FactoryChargesModel(SavingsDatabase db) {
            mDb = db;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChargesViewModel(mDb);
        }
    }

}