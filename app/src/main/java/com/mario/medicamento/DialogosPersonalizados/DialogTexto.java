package com.mario.medicamento.DialogosPersonalizados;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Mario on 17/07/2016.
 */
public class DialogTexto extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return DialogoTexto();
    }


    public Dialog DialogoTexto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Titulo").setMessage("mensaje")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
