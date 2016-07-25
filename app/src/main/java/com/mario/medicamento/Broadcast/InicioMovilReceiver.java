package com.mario.medicamento.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mario.medicamento.Servicios.ServiceMedicamento;

/**
 * Created by Mario on 24/07/2016.
 */
public class InicioMovilReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("msg","receiver inicio!!!");
        context.startService(new Intent(context,ServiceMedicamento.class));
    }
}
