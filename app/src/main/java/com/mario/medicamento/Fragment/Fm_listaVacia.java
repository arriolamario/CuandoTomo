package com.mario.medicamento.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mario.medicamento.R;

/**
 * Created by Mario on 21/07/2016.
 */
public class Fm_listaVacia extends Fragment {
    Button btnAgregar;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_lista_vacia,container,false);

        btnAgregar = (Button) rootView.findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,new Fm_agregarMedicamento()).commit();
                getActivity().setTitle("Agregar");
            }
        });

        return rootView;
    }
}
