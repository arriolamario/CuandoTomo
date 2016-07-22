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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ServiceMedicamento extends Service {

    public static final String TAG = "ServiceMedicamento";
    Timer timer = new Timer();
    int id;
    private static final int TIEMPO = 5000; // en milisegundos
    public ServiceMedicamento() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = recuperarIdUsuario();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                new TareaEnSegundoPlano().execute();

            }
        },0,TIEMPO);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        timer.purge();
    }

    public int recuperarIdUsuario(){
        BufferedReader fin = null;
        String token;
        try {
            fin = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(Contantes.TOKEN)));
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

    public class TareaEnSegundoPlano extends AsyncTask<Void,Void,ArrayList>{
    //1 parametro de entrada para el doInBackground
    //2 parametro de entrada para el onProgressUpdate
    //3 retorno del doInBackground
        @Override
        protected ArrayList doInBackground(Void... params) {
            ArrayList<Medicamento> actualizar = new ArrayList<Medicamento>();
            if(id == -1) return actualizar;

            Usuario usuario = new Usuario(getApplicationContext(),id);
            Calendar actual = Calendar.getInstance();
            for (int i = 0; i < usuario.getListaMedicamentos().size(); i++){
                Medicamento medicamento = usuario.getListaMedicamentos().get(i);
                Calendar siguiente = medicamento.getSiguienteToma();
                if(actual.after(siguiente) && actual.after(medicamento.getFechaInicio())){
                    actualizar.add(medicamento);
                }

            }
            return actualizar;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            for(int i = 0; i < arrayList.size(); i++){
                Medicamento medicamento = (Medicamento) arrayList.get(i);
                medicamento.calcularProximaToma();
                medicamento.modificacion();
                displayNotification(medicamento);

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
            CharSequence contentTitle = getString(R.string.MENSAJE_NOTIFICACION);
            CharSequence contentText = medicamento.usuario.getNombre() + " tiene que tomar " +medicamento.getNombre();
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
