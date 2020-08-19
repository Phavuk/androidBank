package com.pavuk.myapplication.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pavuk.myapplication.db.entity.CategoryPrice;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;

import java.util.List;

@Dao
public interface IncomeDao {

    @Query("SELECT * FROM income_table")
    LiveData<List<Income>> loadAllIncomes();

    @Insert
    void insertIncome(Income income);

    @Query("DELETE FROM income_table")
    void deleteAllIncomes();

    @Query("SELECT SUM(price) FROM income_table ")
    LiveData<Double> getIncomeSum();

    @Query("SELECT category, SUM(price) as price FROM income_table GROUP BY category")
    List<CategoryPrice> getPriceIncomeSumByCategory();

}
