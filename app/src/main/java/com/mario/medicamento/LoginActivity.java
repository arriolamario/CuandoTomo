package com.mario.medicamento;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mario.medicamento.Clase.Usuario;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {

    EditText etUsuario, etClave;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notificacionCancelar();
        Loegeado();
        etUsuario = (EditText) findViewById(R.id.etUsername);
        etClave = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnEntrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String clave = etClave.getText().toString();
                Usuario u = new Usuario(getApplication(), null, null, usuario, clave, 1);
                if (!ControlarCampos(u)) {
                    return;
                }

                if (u.login()) {
                    LoginTrue(u);
                } else {
                    LoginFalse();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void registrar(View v){
        Intent act = new Intent(this, RegistrarActivity.class);
        startActivity(act);
    }

    public void grabarId(int id){
        // Abrir un fichero de salida privado a la aplicación
        OutputStreamWriter fout= null;
        try {
            fout = new OutputStreamWriter(
                    openFileOutput(Contantes.TOKEN, Context.MODE_PRIVATE));
            fout.write(Integer.toString(id));
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoginTrue(Usuario usuario){
        grabarId(usuario.getId());
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.putExtra("id", usuario.getId());
        startActivity(intent);
    }

    private void LoginFalse(){
        Toast.makeText(getApplicationContext(),getString(R.string.LOGIN_CORRECTO), Toast.LENGTH_LONG).show();
    }

    private Boolean ControlarCampos(Usuario u){
        if(u.getUsuario().length() == 0){
            Toast.makeText(this,getString(R.string.FALTA_USUARIO),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(u.getClave().length() == 0){
            Toast.makeText(this,getString(R.string.FALTA_CLAVE),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void Loegeado(){
        BufferedReader fin = null;
        String token;
        try {
            fin = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(Contantes.TOKEN)));
            token = fin.readLine();
            fin.close();
            if(token == null) return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Intent act = new Intent(this, MenuActivity.class);
        act.putExtra("id", Integer.parseInt(token));
        startActivity(act);

    }

    private void notificacionCancelar() {
        int notificationID = getIntent().getIntExtra(getString(R.string.NOTIFICACION),-1);
        if(notificationID != -1) {
            Log.i("notificacion activity", "entra " + notificationID);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.cancel(ServiceMedicamento.TAG, notificationID);
            //startActivity(new Intent(this, LoginActivity.class));
        }else{
            Log.i("notificacion activity", "no entra " + notificationID);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
