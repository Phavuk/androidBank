package com.pavuk.myapplication.screens;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pavuk.myapplication.db.Dao.ChargeDao;
import com.pavuk.myapplication.db.Dao.IncomeDao;
import com.pavuk.myapplication.db.SavingsDatabase;
import com.pavuk.myapplication.db.entity.CategoryPrice;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainScreenViewModel extends ViewModel {


    private SavingsDatabase db;
    private MutableLiveData<List<CategoryPrice>> chargeList;

    /*** constructor */
    public MainScreenViewModel(SavingsDatabase db) {
        this.db = db;
    }

    /** livedata incomeSum */
    LiveData<Double> getChargeSum() {
        return db.chargeDao().getChargeSum();
    }

    /** livedata incomeSum */
    LiveData<Double> getIncomeSum() {
        return db.incomeDao().getIncomeSum();
    }

    LiveData<List<CategoryPrice>> getNamePrice() throws ExecutionException, InterruptedException {
        if (chargeList == null) {
            chargeList = new MutableLiveData<List<CategoryPrice>>();
            getCategoryPricelist();
        }
        return chargeList;
    }

    /** Insert Charge */
    public void insertCharge(Charge charge) {
        new InsertChargeAsyncTask(db.chargeDao()).execute(charge);
        try {
            getCategoryPricelist();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Insert Income */
    public void insertIncome(Income income) {
        new InsertIncomeAsyncTask(db.incomeDao()).execute(income);
        try {
            getCategoryPricelist();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Income List
     * */
    public void getCategoryPricelist() throws ExecutionException, InterruptedException {
       List<CategoryPrice> list = new GetIncomeListAsyncTask(db.chargeDao(),db.incomeDao()).execute().get();
       chargeList.setValue(list);
    }

    public void deleteAllRecords(){
        new DeleteAllAsyncTask(db.chargeDao(),db.incomeDao()).execute();
        try {
            getCategoryPricelist();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class InsertChargeAsyncTask extends AsyncTask<Charge, Void, Void> {

        private ChargeDao chargeDao;

        private InsertChargeAsyncTask(ChargeDao chargeDao) {
            this.chargeDao = chargeDao;
        }

        @Override
        protected Void doInBackground(Charge... charges) {
            chargeDao.insertCharge(charges[0]);
            return null;
        }
    }

    private static class InsertIncomeAsyncTask extends AsyncTask<Income, Void, Void> {

        private IncomeDao incomeDao;

        private InsertIncomeAsyncTask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.insertIncome(incomes[0]);
            return null;
        }
    }

    private static class GetIncomeListAsyncTask extends AsyncTask<IncomeDao, ChargeDao, List<CategoryPrice>> {

        private ChargeDao chargeDao;
        private IncomeDao incomeDao;

        public GetIncomeListAsyncTask(ChargeDao chargeDao, IncomeDao incomeDao) {
            this.chargeDao = chargeDao;
            this.incomeDao = incomeDao;
        }

        @Override
        protected List<CategoryPrice> doInBackground(IncomeDao... incomeDaos) {
            List<CategoryPrice> newList = new ArrayList<CategoryPrice>(chargeDao.getPriceChargeSumByCategory());
            newList.addAll(incomeDao.getPriceIncomeSumByCategory());
            return newList;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<IncomeDao, ChargeDao, List<CategoryPrice>> {

        private ChargeDao chargeDao;
        private IncomeDao incomeDao;

        public DeleteAllAsyncTask(ChargeDao chargeDao, IncomeDao incomeDao) {
            this.chargeDao = chargeDao;
            this.incomeDao = incomeDao;
        }

        @Override
        protected List<CategoryPrice> doInBackground(IncomeDao... incomeDaos) {
            chargeDao.deleteAllCharges();
            incomeDao.deleteAllIncomes();
            return null;
        }
    }

    static class Factory implements ViewModelProvider.Factory {
        private Application mApplication;
        private SavingsDatabase mDb;


        public Factory(SavingsDatabase db) {
            mDb = db;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainScreenViewModel(mDb);
        }
    }

}

