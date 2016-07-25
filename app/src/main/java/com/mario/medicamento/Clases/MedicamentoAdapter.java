package com.mario.medicamento.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Mario on 05/07/2016.
 */
public class MedicamentoAdapter {
    public static final String TABLE_NAME = "medicamento";

    public Cursor getMedicamentoID(Medicamento medicamento) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Col.NOMBRE + " = ? AND " + Col.FECHAINICIO + " = ?";
        String[] params = new String[]{medicamento.getNombre(), medicamento.getFechaInicioString()};
        return db.rawQuery(query,params);
    }

    public class Col implements BaseColumns {
        public final static String ID = "idMedicamento";
        public final static String IDUSUARIO = "idUsuario";
        public final static String NOMBRE = "nombre";
        public final static String FECHAINICIO = "fechaInicio";
        public final static String FECHAFIN = "fechaFin";
        public final static String PROXIMATOMA = "proximaToma";
        public final static String INTERVALO = "intervalo";
        public final static String TIPO_INT = "tipoIntervalo";
    }

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + Col.ID + " integer primary key autoincrement, "
            + Col.NOMBRE + " text not null, "
            + Col.FECHAINICIO + " text not null, "
            + Col.FECHAFIN + " text not null, "
            + Col.INTERVALO + " integer not null, "
            + Col.IDUSUARIO + " integer not null, "
            + Col.PROXIMATOMA + " text no null, "
            + Col.TIPO_INT + " text not null);";

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public MedicamentoAdapter(Context context){
        dbHelper = DataBaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Medicamento medicamento){
        return db.insert(TABLE_NAME,null,generarValues(medicamento));
    }

    private ContentValues generarValues(Medicamento medicamento){
        ContentValues values = new ContentValues();

        values.put(Col.FECHAINICIO, medicamento.getFechaInicioString());
        values.put(Col.FECHAFIN, medicamento.getFechaFinString());
        values.put(Col.PROXIMATOMA, medicamento.getSiguienteTomaString());
        values.put(Col.INTERVALO, medicamento.getIntervalo());
        values.put(Col.NOMBRE, medicamento.getNombre());
        values.put(Col.IDUSUARIO, medicamento.usuario.getId());
        values.put(Col.TIPO_INT, medicamento.getTipoIntervalo());

        return values;
    }

    public  int delete(int id){
        return db.delete(TABLE_NAME,Col.ID + "=?", new String[]{String.valueOf(id)});
    }

    public int update(Medicamento medicamento){
        return  db.update(TABLE_NAME,generarValues(medicamento),Col.ID + "=?",new String[]{String.valueOf(medicamento.getId())});
    }

}
