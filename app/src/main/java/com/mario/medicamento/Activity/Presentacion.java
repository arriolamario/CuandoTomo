package com.mario.medicamento.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.mario.medicamento.R;
import com.mario.medicamento.Servicios.ServiceMedicamento;

/**
 * Created by Mario on 19/07/2016.
 */
public class Presentacion extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        startService(new Intent(getApplicationContext(),ServiceMedicamento.class));


        new Async().execute();
    }

    public class Async extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }
    }
}
