package com.mario.medicamento.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Mario on 05/07/2016.
 */
public class UsuarioAdapter{
    public static final String TABLE_NAME = "usuario";

    public static class Col implements BaseColumns {
        public final static String ID = "idUsuario";
        public final static String NOMBRE = "nombre";
        public final static String APELLIDO = "apellido";
        public final static String USUARIO = "usuario";
        public final static String NOTIFICACION = "noti";

        public final static String[] arregloColumnas(){
            String[] arrglo = new String[] {ID,NOMBRE,APELLIDO,USUARIO};
            return arrglo;
        }
    }

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + Col.ID + " integer primary key autoincrement,"
            + Col.APELLIDO + " text,"
            + Col.NOMBRE + " text,"
            + Col.NOTIFICACION + " integer not null,"
            + Col.USUARIO + " text not null);";

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public UsuarioAdapter(Context context){

        dbHelper = DataBaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    private ContentValues generarValues(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put(Col.APELLIDO,usuario.getApellido());
        valores.put(Col.NOMBRE,usuario.getNombre());
        valores.put(Col.USUARIO, usuario.getUsuario());
        valores.put(Col.NOTIFICACION, usuario.getNotificacion());

        return  valores;
    }

    public long insert(Usuario usuario){


        return db.insert(TABLE_NAME,null,generarValues(usuario));
    }

    public int delete(String id){
        return db.delete(TABLE_NAME, Col.ID + "=?", new String[]{id});
    }

    public int update(Usuario usuario){
        return db.update(TABLE_NAME, generarValues(usuario), Col.ID + "=?", new String[]{String.valueOf(usuario.getId())});
    }

    public boolean existeUsuario(Usuario usuario){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Col.USUARIO + " = ?";
        String[] params = new String[] {usuario.getUsuario()};
        return db.rawQuery(query,params).getCount() == 1;
    }

    public Cursor loginUsuario(Usuario usuario){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Col.USUARIO + " = ?";
        String[] params = new String[]{usuario.getUsuario()};

        return db.rawQuery(query,params);
    }

    public Cursor getUsuarioById(Usuario usuario){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Col.ID + " = ?";
        String[] params = new String[] {Integer.toString(usuario.getId())};

        return db.rawQuery(query,params);
    }

    public Cursor getMedicamentos(Usuario usuario){
        String query = "SELECT * FROM " + MedicamentoAdapter.TABLE_NAME + " WHERE " + MedicamentoAdapter.Col.IDUSUARIO + " = ?";
        String[] params = new String[]{Integer.toString(usuario.getId())};

        return db.rawQuery(query,params);
    }

    public Cursor getUsuarioId(Usuario usuario) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Col.USUARIO + " = ?";
        String[] params = new String[] {usuario.getUsuario()};

        return db.rawQuery(query,params);
    }

    public Cursor getUsuarios() {
        String query = "SELECT * FROM " + TABLE_NAME;
        String[] params = new String[0];

        return db.rawQuery(query,params);
    }








}
