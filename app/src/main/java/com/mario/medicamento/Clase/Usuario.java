package com.mario.medicamento.Clase;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Mario
 * @version 1.0
 * @created 05-jul.-2016 14:24:39
 */
public class Usuario {

	private int id;
	private String nombre;
	private String apellido;
	private String usuario;
	public ArrayList<Medicamento> listaMedicamentos;
    private UsuarioAdapter adapter;
    private int notificacion;

    public Usuario(Context context){
        adapter = new UsuarioAdapter(context);
	}


    /**
	 * 
	 * @exception Throwable
	 */
	public void finalize()
	  throws Throwable{

	}

    public Usuario(Context context, String nombre, String apellido, String usuario, int notificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.notificacion = notificacion;
        adapter = new UsuarioAdapter(context);
    }

	public Boolean alta() {
        this.id = (int) adapter.insert(this);

        return id>0;
        //return true si se agrego una fila en la tabla, false de lo contrario
	}

	public void baja() {
        adapter.delete(String.valueOf(id));
	}

	public void modificacion() {
        adapter.update(this);
	}

    public boolean login(){
        boolean login;
        Cursor cur = adapter.loginUsuario(this);
        if(cur.getCount() == 1){
            login = true;

            CargarUsuario(cur);


        }else{
            login = false;
        }
        return login;
    }

    private void CargarUsuario(Cursor cur) {


        if(cur.moveToFirst()) {
            this.id = cur.getInt(cur.getColumnIndex(UsuarioAdapter.Col.ID));
            this.nombre = cur.getString(cur.getColumnIndex(UsuarioAdapter.Col.NOMBRE));
            this.apellido = cur.getString(cur.getColumnIndex(UsuarioAdapter.Col.APELLIDO));
            this.usuario = cur.getString(cur.getColumnIndex(UsuarioAdapter.Col.USUARIO));
            this.notificacion = cur.getInt(cur.getColumnIndex(UsuarioAdapter.Col.NOTIFICACION));
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean existeUsuario(){
        return adapter.existeUsuario(this);
        //return true si existe, false si no existe
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt(UsuarioAdapter.Col.ID,id);
        bundle.putString(UsuarioAdapter.Col.NOMBRE, nombre);
        bundle.putString(UsuarioAdapter.Col.APELLIDO, apellido);
        bundle.putString(UsuarioAdapter.Col.USUARIO, usuario);
        return bundle;
    }

    public Usuario(Context context, int id){
        adapter = new UsuarioAdapter(context);
        this.id = id;
        Cursor cur = adapter.getUsuarioById(this);
        CargarUsuario(cur);
        cargarMedicamentos(context);
    }

    private void cargarMedicamentos(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Cursor cur = adapter.getMedicamentos(this);
        listaMedicamentos = new ArrayList<Medicamento>();
        if(cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                try {
                    int id = cur.getInt(cur.getColumnIndex(MedicamentoAdapter.Col.ID));
                    String nombre = cur.getString(cur.getColumnIndex(MedicamentoAdapter.Col.NOMBRE));
                    int intervalo = cur.getInt(cur.getColumnIndex(MedicamentoAdapter.Col.INTERVALO));
                    Date inicio = sdf.parse(cur.getString(cur.getColumnIndex(MedicamentoAdapter.Col.FECHAINICIO)));
                    Calendar auxInicio = Calendar.getInstance();
                    auxInicio.setTime(inicio);

                    Date fin = sdf.parse(cur.getString(cur.getColumnIndex(MedicamentoAdapter.Col.FECHAFIN)));
                    Calendar auxFin = Calendar.getInstance();
                    auxFin.setTime(fin);

                    Date sig = sdf.parse(cur.getString(cur.getColumnIndex(MedicamentoAdapter.Col.PROXIMATOMA)));
                    Calendar auxSig = Calendar.getInstance();
                    auxSig.setTime(sig);
                    String tipoIntervalo = cur.getString(cur.getColumnIndex(MedicamentoAdapter.Col.TIPO_INT));

                    Medicamento medicamento = new Medicamento(context,id,nombre,auxInicio,auxFin,auxSig,intervalo, tipoIntervalo);
                    medicamento.setUsuario(this);
                    listaMedicamentos.add(medicamento);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while(cur.moveToNext());
        }
    }

    public ArrayList<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void mostrarDatos(){
        Log.i("msg usuario id" , this.id +"");
        Log.i("msg usuario nombre" , this.nombre );
        Log.i("msg usuario apellido" , this.apellido );
        Log.i("msg usuario usuario" , this.usuario );
        Log.i("msg usuario notifiacion", this.notificacion + "");
    }

    public int getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(int notificacion){
        this.notificacion = notificacion;
    }

    public static ArrayList getUsuarios(Context context){
        ArrayList<Usuario> arrayList = new ArrayList<>();
        UsuarioAdapter ua = new UsuarioAdapter(context);
        Cursor cur = ua.getUsuarios();
        if(cur.moveToFirst()){
            do {
                int id = cur.getInt(cur.getColumnIndex(UsuarioAdapter.Col.ID));
                Usuario usuario = new Usuario(context, id);
                arrayList.add(usuario);
            }while (cur.moveToNext());
        }

        return arrayList;
    }
}//end Usuario