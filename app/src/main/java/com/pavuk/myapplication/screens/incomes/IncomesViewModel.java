package com.pavuk.myapplication.screens.incomes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pavuk.myapplication.db.Dao.IncomeDao;
import com.pavuk.myapplication.db.SavingsDatabase;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;
import com.pavuk.myapplication.screens.charges.ChargesViewModel;

import java.util.List;

public class IncomesViewModel extends ViewModel {

    private SavingsDatabase db;

    /** constructor */
    public IncomesViewModel(SavingsDatabase db) {
        this.db = db;
    }

    /** livedata to observe charges list from db */
    LiveData<List<Income>> getAllIncomes() {
        return db.incomeDao().loadAllIncomes();
    }

    static class FactoryIncomesModel implements ViewModelProvider.Factory {
        private SavingsDatabase mDb;

        public FactoryIncomesModel(SavingsDatabase db) {
            mDb = db;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new IncomesViewModel(mDb);
        }
    }
}