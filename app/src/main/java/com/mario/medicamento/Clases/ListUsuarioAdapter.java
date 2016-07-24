package com.mario.medicamento.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mario.medicamento.Clase.Medicamento;
import com.mario.medicamento.Clase.Usuario;
import com.mario.medicamento.R;

import java.util.List;

/**
 * Created by Mario on 24/07/2016.
 */
public class ListUsuarioAdapter extends ArrayAdapter {

    public ListUsuarioAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.usuario_item, null);
        }

        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombreUsuario);

        Usuario item = (Usuario) getItem(position);
        nombre.setText(item.getUsuario());

        return convertView;
    }
}
