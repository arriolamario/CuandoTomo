package com.mario.medicamento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Mario on 24/06/2016.
 */
public class fm_salir extends Fragment {
    View rooView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rooView = inflater.inflate(R.layout.fm_salir,container,false);
        new Dialogo().show(getFragmentManager(), "SALIR");
        return rooView;
    }


    public void Salir(){
        // Abrir un fichero de salida privado a la aplicación
        OutputStreamWriter fout= null;
        try {
            fout = new OutputStreamWriter(getActivity().openFileOutput(Contantes.TOKEN,Context.MODE_PRIVATE));
            fout.write("");
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
    }


    public class Dialogo extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rooView.getContext());
            builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Salir();
                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame, new fm_listarMedicamentos()).commit();

                }
            }).setTitle("Alerta").setMessage("¿Esta seguro que quiere salir?").setIcon(R.drawable.alerta);

            return builder.create();
        }
    }


}
