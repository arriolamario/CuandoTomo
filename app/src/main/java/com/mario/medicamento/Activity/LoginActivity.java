package com.mario.medicamento.Activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mario.medicamento.Clases.Constantes;
import com.mario.medicamento.Clases.Usuario;
import com.mario.medicamento.Clases.ListUsuarioAdapter;
import com.mario.medicamento.R;
import com.mario.medicamento.Servicios.ServiceMedicamento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ListView lvLista;
    ArrayList<Usuario> listaUsuario;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notificacionCancelar();
        Loegeado();
        IrARegistrar();
        lvLista = (ListView) findViewById(R.id.lvListaUsuario);
        actualizarLista();


        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = new Usuario(getApplicationContext(), listaUsuario.get(position).getId());

                if (usuario.login()) {
                    LoginTrue(usuario);
                } else {
                    LoginFalse();
                }

            }
        });

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),RegistrarActivity.class));
            }
        });

    }

    private void actualizarLista() {
        listaUsuario = Usuario.getUsuarios(getApplicationContext());
        lvLista.setAdapter(new ListUsuarioAdapter(getApplicationContext(), listaUsuario));
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }

    public void grabarId(int id){
        // Abrir un fichero de salida privado a la aplicación
        OutputStreamWriter fout= null;
        try {
            fout = new OutputStreamWriter(
                    openFileOutput(Constantes.TOKEN, Context.MODE_PRIVATE));
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

    private void Loegeado(){
        BufferedReader fin = null;
        String token;
        try {
            fin = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(Constantes.TOKEN)));
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

    private void IrARegistrar(){
        listaUsuario = Usuario.getUsuarios(getApplicationContext());

        if (listaUsuario.size() == 0){
            startActivity(new Intent(getApplicationContext(),RegistrarActivity.class));
        }
    }
}
