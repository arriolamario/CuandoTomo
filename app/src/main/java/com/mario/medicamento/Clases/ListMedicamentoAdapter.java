package com.mario.medicamento.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mario.medicamento.R;

import java.util.Calendar;
import java.util.List;


/**
 * Created by Mario on 16/02/2016.
 */
public class ListMedicamentoAdapter extends ArrayAdapter {

    public ListMedicamentoAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_medicamento_item, null);
        }

        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView fechaFin = (TextView) convertView.findViewById(R.id.tvFechaFin);
        TextView proxima = (TextView) convertView.findViewById(R.id.tvSiguienteToma);
        TextView fechaInicio = (TextView) convertView.findViewById(R.id.tvFechaInicio);
        TextView restante = (TextView) convertView.findViewById(R.id.tvRestante);
        Medicamento item = (Medicamento)getItem(position);
        nombre.setText(item.getNombre());
        fechaFin.setText(item.getFechaFinString());
        proxima.setText(item.getSiguienteTomaString());
        fechaInicio.setText(item.getFechaInicioString());

        restante.setText(calcular(item));
        return convertView;
    }

    private String calcular(Medicamento medicamento) {

        long diff = medicamento.getSiguienteToma().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

        long seconds =(diff/1000)%60;
        long minutes=(diff/(1000*60))%60;
        long hours=(diff/(1000*60*60))%24;
        long days=(diff/(1000*60*60*24))%365;

        String min = String.valueOf(minutes);
        if(minutes < 10){
            min = "0"+min;
        }



        if(minutes<0){
            min = "00";
        }
        String hs = String.valueOf(hours);
        if(hours < 0){
            hs = "0";
        }

        String res = "";
        if(hours <= 0 & minutes <= 0){
            res = "<1";
        }
        else{
            res = hs +":"+min;
        }

        if(days > 0){
            res = days + "dias " + res;
        }
        return res;
    }

}
