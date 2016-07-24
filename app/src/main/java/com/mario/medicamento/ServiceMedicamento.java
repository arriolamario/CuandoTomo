package com.mario.medicamento;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.mario.medicamento.Clase.Medicamento;
import com.mario.medicamento.Clase.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ServiceMedicamento extends Service {

    public static final String TAG = "ServiceMedicamento";
    Timer timer = new Timer();
    private static final int TIEMPO = 5; // en segundos
    public ServiceMedicamento() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("msg","comienza el service");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                new TareaEnSegundoPlano().execute();

            }
        }, 0, TIEMPO * 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }


    public class TareaEnSegundoPlano extends AsyncTask<Void,Void,ArrayList>{
    //1 parametro de entrada para el doInBackground
    //2 parametro de entrada para el onProgressUpdate
    //3 retorno del doInBackground
        @Override
        protected ArrayList doInBackground(Void... params) {
            ArrayList<Medicamento> actualizar = new ArrayList<>();

            ArrayList<Usuario> usuarios = Usuario.getUsuarios(getApplicationContext());
            Calendar actual = Calendar.getInstance();
            for(int j = 0; j< usuarios.size(); j++){
                Usuario u = usuarios.get(j);
                    for (int i = 0; i < u.getListaMedicamentos().size(); i++) {
                        Medicamento medicamento = u.getListaMedicamentos().get(i);
                        Calendar siguiente = medicamento.getSiguienteToma();
                        if (actual.after(siguiente) && actual.after(medicamento.getFechaInicio())) {
                            actualizar.add(medicamento);
                        }


                    }
            }

            /**/
            return actualizar;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            for(int i = 0; i < arrayList.size(); i++){
                Medicamento medicamento = (Medicamento) arrayList.get(i);
                medicamento.calcularProximaToma();
                medicamento.modificacion();
                if(medicamento.usuario.getNotificacion() == 1) {
                    displayNotification(medicamento);
                }
                if(medicamento.getSiguienteToma().after(medicamento.getFechaFin())){
                    medicamento.baja();
                }
            }
        }



        protected void displayNotification(Medicamento medicamento){
            Intent i = new Intent(getApplication(), LoginActivity.class);
            int idNoti = (int) Calendar.getInstance().getTimeInMillis();
            i.putExtra(getString(R.string.NOTIFICACION),idNoti);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), idNoti, i, 0);
            NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            CharSequence ticker = medicamento.usuario.getNombre();
            CharSequence contentTitle = medicamento.usuario.getApellido() + " " + medicamento.usuario.getNombre();
            CharSequence contentText = "Tiene que tomar " + medicamento.getNombre();
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification noti = new NotificationCompat.Builder(getApplication())
                    .setTicker(ticker)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSmallIcon(R.drawable.notificacion)
                    .setVibrate(new long[] {100, 250, 100, 500})
                    .setSound(alarmSound)
                    .build();
            nm.notify(TAG,idNoti, noti);
            Log.i("notificacion service",idNoti+"");
        }
    }
}
