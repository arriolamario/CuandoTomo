package com.mario.medicamento.Clase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mario on 05/07/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbMedicamentos.sqlite";
    private static final int VERSION = 1;
    private static DataBaseHelper mInstance = null;

    public DataBaseHelper(Context context) {
        super(context,DB_NAME, null, VERSION);
    }


    public static DataBaseHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DataBaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioAdapter.CREATE_TABLE);
        db.execSQL(MedicamentoAdapter.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
