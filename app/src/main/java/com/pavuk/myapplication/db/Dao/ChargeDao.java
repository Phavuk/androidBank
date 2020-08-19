package com.pavuk.myapplication.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.CategoryPrice;

import java.util.List;

@Dao
public interface ChargeDao {

    @Query("SELECT * FROM charge_table")
    LiveData<List<Charge>> loadAllCharges();

    @Insert
    void insertCharge(Charge charge);

    @Query("DELETE FROM charge_table")
    void deleteAllCharges();

    @Query("SELECT SUM(price) FROM charge_table ")
    LiveData<Double> getChargeSum();

    @Query("SELECT category, SUM(price) as price FROM charge_table GROUP BY category")
    List<CategoryPrice> getPriceChargeSumByCategory();

}
