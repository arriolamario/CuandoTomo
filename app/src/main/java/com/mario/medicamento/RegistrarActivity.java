package com.mario.medicamento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.mario.medicamento.Clases.Usuario;

/**
 * Created by Mario on 18/06/2016.
 */
public class RegistrarActivity extends Activity {
    EditText etUsuario, etClave, etNombre, etApellido;
    Button registrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        //notificacionCancelar();
        etUsuario = (EditText) findViewById(R.id.etUsername);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etNombre = (EditText) findViewById((R.id.etNombre));

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ControlarCampos()) {
                    return;
                }
                final String usuario = etUsuario.getText().toString();
                final String apellido = etApellido.getText().toString();
                final String nombre = etNombre.getText().toString();
                Usuario u = new Usuario(getApplication(), nombre, apellido, usuario, 1);
                if (u.existeUsuario()) {
                    Toast.makeText(getBaseContext(), getString(R.string.USUARIO_EXISTENTE), Toast.LENGTH_LONG).show();
                } else {
                    if (u.alta()){
                        Toast.makeText(getBaseContext(), getString(R.string.REGISTRO_CORRECTO), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                    else {
                        Toast.makeText(getBaseContext(), getString(R.string.REGISTRO_INCORRECTO), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private Boolean ControlarCampos(){
        if(etNombre.getText().length() == 0) {
            Toast.makeText(getApplicationContext(),getString(R.string.FALTA_NOMBRE),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etApellido.getText().length() == 0){
            Toast.makeText(getApplicationContext(),getString(R.string.FALTA_APELLIDO),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etUsuario.getText().length() == 0){
            Toast.makeText(getApplicationContext(),getString(R.string.FALTA_USUARIO),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



}

