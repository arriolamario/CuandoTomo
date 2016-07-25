package com.mario.medicamento.Clases;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Mario
 * @version 1.0
 * @created 05-jul.-2016 14:24:39
 */
public class Medicamento {

	private int id;
	private String nombre;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private int intervalo;
	private Calendar siguienteToma;
	public Usuario usuario;
	private MedicamentoAdapter adapter;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	private String tipoIntervalo;

	public Medicamento(Context context, Usuario usuario){
		adapter = new MedicamentoAdapter(context);
		this.usuario = usuario;
	}

	public Medicamento() {

	}

	public Medicamento(Context context, int id, String nombre, Calendar auxInicio, Calendar auxFin, Calendar auxSig, int intervalo, String tipoIntervalo) {
		adapter = new MedicamentoAdapter(context);
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = auxInicio;
		this.fechaFin = auxFin;
		this.siguienteToma = auxSig;
		this.intervalo = intervalo;
		this.tipoIntervalo = tipoIntervalo;

	}

	public Medicamento(Context context, String nombre, Calendar fechaFin, int intervalo, Usuario usuario, String tipoIntervalo) {
		this.nombre = nombre;
		this.fechaFin = fechaFin;
		this.intervalo = intervalo;
		this.usuario = usuario;
		this.tipoIntervalo = tipoIntervalo;
		siguienteToma = Calendar.getInstance();
		calcularProximaToma();
		adapter = new MedicamentoAdapter(context);
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize()
	  throws Throwable{

	}

	public boolean alta(){
		id = (int) adapter.insert(this);

		return  id>0;
	}

	public void baja(){	adapter.delete(id);	}

	public boolean modificacion(){
		return adapter.update(this)>0;
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

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Calendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	public Calendar getSiguienteToma() {
		return siguienteToma;
	}

	public void setSiguienteToma(Calendar siguienteToma) {
		this.siguienteToma = siguienteToma;
	}

	public String getFechaInicioString() {
		return sdf.format(fechaInicio.getTime());
	}

	public String getFechaFinString() {
		return sdf.format(fechaFin.getTime());
	}

	public String getSiguienteTomaString() {
		return sdf.format(siguienteToma.getTime());
	}

	public Medicamento(Context context, String nombre, Calendar fechaInicio, Calendar fechaFin, int intervalo, Usuario usuario, String tipoIntervalo) {
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.intervalo = intervalo;
		this.usuario = usuario;
		this.tipoIntervalo = tipoIntervalo;

		try {
			Date aux = sdf.parse(getFechaInicioString());
			siguienteToma = Calendar.getInstance();
			siguienteToma.setTime(aux);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calcularProximaToma();
		adapter = new MedicamentoAdapter(context);
	}

	public void calcularProximaToma() {

		siguienteToma.add(Calendar.MINUTE, intervalo);

	}

	public void setId(int id) {
		this.id = id;
	}


	public void mostrarDatos(){
		Log.i("msg medicamento id" , this.id +"");
		Log.i("msg medicamento nombre" , this.nombre );
		Log.i("msg medicamento inicio" , getFechaInicioString() );
		Log.i("msg medicamento fin" , getFechaFinString() );
		Log.i("msg medicamento siguiente" , getSiguienteTomaString() );
		Log.i("msg medicamento intervalo" , this.intervalo +"" );
		Log.i("msg medicamento tipo intervalo" , this.tipoIntervalo);
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipoIntervalo() {
		return tipoIntervalo;
	}

	public void setTipoIntervalo(String tipoIntervalo) {
		this.tipoIntervalo = tipoIntervalo;
	}
}//end Medicamento