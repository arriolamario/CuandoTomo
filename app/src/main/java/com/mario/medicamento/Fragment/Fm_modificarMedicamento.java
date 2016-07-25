package com.mario.medicamento.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by Mario on 17/02/2016.
 */
public class Fm_modificarMedicamento extends Fragment{

    View rootView;
    SimpleDateFormat sdfDate;
    SimpleDateFormat sdfHour;
    TextView tvFechaInicio, tvHoraInicio, tvFechaFin, tvHoraFin;
    EditText etNombre, etIntervalo;
    RadioButton rbDia, rbHora, rbMin;
    final Calendar calendar = Calendar.getInstance();
    int añoI, mesI, diaI, añoF, mesF, diaF;
    int horaI, minI, horaF, minF;
    Medicamento medicamento;

    public Fm_modificarMedicamento() {
    }

    public Fm_modificarMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
        añoI = medicamento.getFechaInicio().get(Calendar.YEAR);
        mesI = medicamento.getFechaInicio().get(Calendar.MONTH);
        diaI = medicamento.getFechaInicio().get(Calendar.DAY_OF_MONTH);
        añoF = medicamento.getFechaFin().get(Calendar.YEAR);
        mesF = medicamento.getFechaFin().get(Calendar.MONTH);
        diaF = medicamento.getFechaFin().get(Calendar.DAY_OF_MONTH);
        horaI = medicamento.getFechaInicio().get(Calendar.HOUR_OF_DAY);
        horaF = medicamento.getFechaFin().get(Calendar.HOUR_OF_DAY);
        minI = medicamento.getFechaInicio().get(Calendar.MINUTE);
        minF = medicamento.getFechaFin().get(Calendar.MINUTE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_modificar_medicamento, container, false);
        setHasOptionsMenu(true);
        sdfDate = new SimpleDateFormat(getResources().getString(R.string.FECHA));
        sdfHour = new SimpleDateFormat(getResources().getString(R.string.HORA));

        etNombre = (EditText) rootView.findViewById(R.id.etNombre);
        tvFechaInicio = (TextView) rootView.findViewById(R.id.tvFechaInicio);
        tvHoraInicio = (TextView) rootView.findViewById(R.id.tvHoraInicio);
        tvFechaFin = (TextView) rootView.findViewById(R.id.tvFechaFin);
        tvHoraFin = (TextView) rootView.findViewById(R.id.tvHoraFin);
        etIntervalo = (EditText) rootView.findViewById(R.id.etIntervalo);
        rbDia = (RadioButton) rootView.findViewById(R.id.rbDias);
        rbHora = (RadioButton) rootView.findViewById(R.id.rbHoras);
        rbMin = (RadioButton) rootView.findViewById(R.id.rbMin);

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
                        calendar.set(añoF, mesF, diaF, horaF, minF);
                        tvHoraFin.setText(sdfHour.format(calendar.getTime()));
                    }
                }, horaF, minF, true);
                timePickerDialog.show();
            }
        });

        return rootView;
    }

    private void cargarMedicamento() {
        etNombre.setText(medicamento.getNombre());
        sdfHour = new SimpleDateFormat(getResources().getString(R.string.HORA));
        sdfDate = new SimpleDateFormat(getString(R.string.FECHA));
        String fechaInicio = sdfDate.format(medicamento.getFechaInicio().getTime());
        String horaInicio = sdfHour.format(medicamento.getFechaInicio().getTime());
        String fechaFin = sdfDate.format(medicamento.getFechaFin().getTime());
        String horaFin = sdfHour.format(medicamento.getFechaFin().getTime());
        tvFechaInicio.setText(fechaInicio);
        tvHoraInicio.setText(horaInicio);
        tvFechaFin.setText(fechaFin);
        tvHoraFin.setText(horaFin);
        int intervalo = medicamento.getIntervalo();
        if(medicamento.getTipoIntervalo().compareTo(Constantes.DIA) == 0) {
            rbDia.setChecked(true);
            rbMin.setChecked(false);
            rbHora.setChecked(false);
            intervalo = (intervalo / 24)/ 60;

        }
        if(medicamento.getTipoIntervalo().compareTo(Constantes.MINUTO) == 0) {
            rbMin.setChecked(true);
            rbDia.setChecked(false);
            rbHora.setChecked(false);
        }
        if(medicamento.getTipoIntervalo().compareTo(Constantes.HORA)==0) {
            rbMin.setChecked(false);
            rbHora.setChecked(true);
            rbDia.setChecked(false);
            intervalo = intervalo/60;
        }
        medicamento.mostrarDatos();
        etIntervalo.setText(String.valueOf(intervalo));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.modificar_medicamento,menu);

    }

    @Override
    public void onStart() {
        super.onStart();
        cargarMedicamento();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnModificar:
                modificarMedicamento();
                break;
            case R.id.btnEliminar:
                eliminarMedicamento();
        }

        return true;
    }

    private void eliminarMedicamento() {
        Dialogo dialogo = new Dialogo();
        dialogo.show(getFragmentManager(),"1");
    }

    private void modificarMedicamento() {
        if(CamposCompletos()){
            Usuario usuario = new Usuario(getActivity(),recuperarIdUsuario());
            Calendar fin = fechaFinCalendar();
            String nombre = etNombre.getText().toString();
            int intervalo = getIntervalo();
            Context context = rootView.getContext();
            String tipoIntervalo = getTipoIntervalo();

            Medicamento med = new Medicamento(context,nombre,fin,intervalo,usuario, tipoIntervalo);
            med.setFechaInicio(medicamento.getFechaInicio());
            med.setId(medicamento.getId());
            if(med.modificacion()){
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,new Fm_listarMedicamentos()).commit();
                Toast.makeText(context, getString(R.string.MODIFICACION_CORRECTA), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,getString(R.string.MODIFICACION_INCORRECTA),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean CamposCompletos(){

        if(etNombre.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),getString(R.string.FALTA_NOMBRE),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etIntervalo.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(),getString(R.string.FALTA_INTERVALO),Toast.LENGTH_SHORT).show();
            return false;
        }

        Calendar ini = Calendar.getInstance();
        Calendar fin = fechaFinCalendar();

        if(!ini.before(fin)) {
            Toast.makeText(getActivity(),getString(R.string.FECHA_INCORRECTA),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    public class Dialogo extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    medicamento.baja();
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame,new Fm_listarMedicamentos()).commit();
                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setTitle("Alerta").setMessage("¿Esta seguro que quiere eliminar el medicamento?").setIcon(R.drawable.alerta);

            return builder.create();
        }
    }


    public Calendar fechaInicioCalendar(){
        Calendar ini = Calendar.getInstance();
        ini.set(añoI,mesI,diaI,horaI,minI);
        return ini;
    }

    public Calendar fechaFinCalendar(){
        Calendar ini = Calendar.getInstance();
        ini.set(añoF, mesF, diaF, horaF, minF);
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
