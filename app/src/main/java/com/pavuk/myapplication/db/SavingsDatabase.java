package com.pavuk.myapplication.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pavuk.myapplication.db.Dao.ChargeDao;
import com.pavuk.myapplication.db.Dao.IncomeDao;
import com.pavuk.myapplication.db.Dao.SumDao;
import com.pavuk.myapplication.db.entity.Charge;
import com.pavuk.myapplication.db.entity.Income;

/** databáza ktorá sa skladá z tabulky charge_table a income_table*/
@Database(entities = {Charge.class, Income.class}, version = 2,exportSchema = false)
public abstract class SavingsDatabase extends RoomDatabase {

    /** singleton pattern pre vytvorenie len 1 inštancie databazy v celej appke */
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "savings_db";
    private static SavingsDatabase INSTANCE;

    public static SavingsDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SavingsDatabase.class, SavingsDatabase.DATABASE_NAME).build();
            }
        }
        return INSTANCE;
    }

    public abstract ChargeDao chargeDao();
    public abstract IncomeDao incomeDao();

}
