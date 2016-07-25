package com.mario.medicamento.Fragment;


import android.app.DatePickerDialog;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mario.medicamento.Clases.Constantes;
import com.mario.medicamento.Clases.Medicamento;
import com.mario.medicamento.Clases.Usuario;
import com.mario.medicamento.R;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Mario on 09/02/2016.
 */

//Agregar Medicamentos
public class Fm_agregarMedicamento extends Fragment{

    View rootView;
    TextView tvFechaInicio, tvHoraInicio, tvFechaFin, tvHoraFin;
    EditText etNombre, etIntervalo;
    RadioButton rbDia, rbHora, rbMin;
    Medicamento medicamento;
    SimpleDateFormat sdfDate;
    SimpleDateFormat sdfHour;
    boolean setHora, setFecha;
    final Calendar calendar = Calendar.getInstance();
    int añoI, mesI, diaI, añoF, mesF, diaF;
    int horaI, minI, horaF, minF;

    public Fm_agregarMedicamento() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fm_agrega_medicamento, container, false);
        setHasOptionsMenu(true);
        sdfDate = new SimpleDateFormat(getResources().getString(R.string.FECHA));
        sdfHour = new SimpleDateFormat(getResources().getString(R.string.HORA));
        añoI = calendar.get(Calendar.YEAR);
        mesI = calendar.get(Calendar.MONTH);
        diaI = calendar.get(Calendar.DAY_OF_MONTH);
        añoF = añoI;
        mesF = mesI;
        diaF = diaI;
        horaI = calendar.get(Calendar.HOUR_OF_DAY);
        horaF = horaI;
        minI = calendar.get(Calendar.MINUTE);
        minF = minI;
        etNombre = (EditText) rootView.findViewById(R.id.etNombre);
        tvFechaInicio = (TextView) rootView.findViewById(R.id.tvFechaInicio);
        tvHoraInicio = (TextView) rootView.findViewById(R.id.tvHoraInicio);
        tvFechaFin = (TextView) rootView.findViewById(R.id.tvFechaFin);
        tvHoraFin = (TextView) rootView.findViewById(R.id.tvHoraFin);
        etIntervalo = (EditText) rootView.findViewById(R.id.etIntervalo);
        Usuario usuario = new Usuario(getActivity(),recuperarIdUsuario());
        medicamento = new Medicamento(rootView.getContext(),usuario);
        rbDia = (RadioButton) rootView.findViewById(R.id.rbDias);
        rbHora = (RadioButton) rootView.findViewById(R.id.rbHoras);
        rbMin = (RadioButton) rootView.findViewById(R.id.rbMin);
        setFecha = false;
        setHora = false;
        setCarteles();
        tvFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar ca = Calendar.getInstance();
                        ca.set(year, monthOfYear, dayOfMonth);
                        tvFechaInicio.setText(sdfDate.format(ca.getTime()));
                        añoI = year;
                        mesI = monthOfYear;
                        diaI = dayOfMonth;
                    }
                },añoI,mesI,diaI);
                datePickerDialog.show();
            }
        });

        tvHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(rootView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaI = hourOfDay;
                        minI = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(añoI,mesI,diaI,horaI,minI);
                        tvHoraInicio.setText(sdfHour.format(calendar.getTime()));
                    }
                },horaI,minI,true);
                timePickerDialog.show();
            }
        });

        tvFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar ca = Calendar.getInstance();
                        ca.set(year, monthOfYear, dayOfMonth);
                        tvFechaFin.setText(sdfDate.format(ca.getTime()));
                        añoF = year;
                        mesF = monthOfYear;
                        diaF = dayOfMonth;
                        setFecha = true;
                    }
                },añoF,mesF,diaF);

                datePickerDialog.show();
            }
        });

        tvHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(rootView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaF = hourOfDay;
                        minF = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(añoF,mesF,diaF,horaF,minF);
                        tvHoraFin.setText(sdfHour.format(calendar.getTime()));
                        setHora = true;
                    }
                },horaF,minF,true);
                timePickerDialog.show();
            }
        });


        return rootView;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_agregar_medicamento, menu);
    }



   private void  setCarteles(){
        tvFechaInicio.setText(sdfDate.format(calendar.getTime()));
        tvHoraInicio.setText(sdfHour.format(calendar.getTime()));
    }

    private boolean CamposCompletos(){
        if(!setHora & !setFecha){
            Toast.makeText(getActivity(),getString(R.string.FALTA_FECHAFIN),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etNombre.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),getString(R.string.FALTA_NOMBRE),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etIntervalo.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(),getString(R.string.FALTA_INTERVALO),Toast.LENGTH_SHORT).show();
            return false;
        }

        Calendar ini = fechaInicioCalendar();
        Calendar fin = fechaFinCalendar();

        if(!ini.before(fin)) {
            Toast.makeText(getActivity(),getString(R.string.FECHA_INCORRECTA),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.guardar_med:
                if(CamposCompletos()){
                    Usuario usuario = new Usuario(rootView.getContext(),recuperarIdUsuario());
                    Calendar inicio = fechaInicioCalendar();
                    Calendar fin = fechaFinCalendar();
                    String nombre = etNombre.getText().toString();
                    int intervalo = getIntervalo();
                    Context context = rootView.getContext();
                    String tipoIntervalo = getTipoIntervalo();

                    Medicamento medicamento = new Medicamento(context,nombre,inicio,fin,intervalo,usuario, tipoIntervalo);
                    if(medicamento.alta()){
                        FragmentManager fm = getFragmentManager();
                        fm.beginTransaction().replace(R.id.content_frame,new Fm_listarMedicamentos()).commit();
                        Toast.makeText(context,getString(R.string.REGISTRO_CORRECTO),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,getString(R.string.REGISTRO_INCORRECTO),Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                break;
        }

        return true;
    }

    public Calendar fechaInicioCalendar(){
        Calendar ini = Calendar.getInstance();
        ini.set(añoI,mesI,diaI,horaI,minI);
        return ini;
    }

    public Calendar fechaFinCalendar(){
        Calendar ini = Calendar.getInstance();
        ini.set(añoF,mesF,diaF,horaF,minF);
        return ini;
    }

    public int getIntervalo() {
        int intervalo = Integer.parseInt(etIntervalo.getText().toString());
        if(rbDia.isChecked()){ intervalo = intervalo * 24 * 60; }
        if(rbHora.isChecked()){ intervalo = intervalo * 60; }

        return intervalo;
    }

    public String getTipoIntervalo(){
        String res = null;
        if(rbDia.isChecked()){ res = Constantes.DIA; }
        if(rbHora.isChecked()){ res = Constantes.HORA; }
        if(rbMin.isChecked()) { res = Constantes.MINUTO; }

        return  res;
    }

    public int recuperarIdUsuario(){
        BufferedReader fin = null;
        String token;
        try {
            fin = new BufferedReader(
                    new InputStreamReader(
                            getActivity().openFileInput(Constantes.TOKEN)));
            token = fin.readLine();
            fin.close();
            if(token == null) return -1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return Integer.parseInt(token);
    }
}
