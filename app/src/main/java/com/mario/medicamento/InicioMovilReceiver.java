package com.mario.medicamento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Mario on 24/07/2016.
 */
public class InicioMovilReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context,ServiceMedicamento.class));
    }
}
