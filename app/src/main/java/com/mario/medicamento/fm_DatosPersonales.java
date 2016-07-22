package com.mario.medicamento;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mario.medicamento.Clase.Medicamento;
import com.mario.medicamento.Clase.Usuario;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mario on 16/07/2016.
 */
public class fm_DatosPersonales extends Fragment {

    View rootView;
    EditText etNombre, etApellido, etUsuario, etClave;
    Button btnRegistrar;
    Usuario usuario;
    public fm_DatosPersonales() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_registrar, container, false);
        setHasOptionsMenu(true);

        etNombre = (EditText) rootView.findViewById(R.id.etNombre);
        etApellido = (EditText) rootView.findViewById(R.id.etApellido);
        etUsuario = (EditText) rootView.findViewById(R.id.etUsername);
        etClave = (EditText) rootView.findViewById(R.id.etPassword);
        btnRegistrar = (Button) rootView.findViewById(R.id.btnRegistrar);

        btnRegistrar.setVisibility(View.INVISIBLE);
        int idUsuario = recuperarIdUsuario();

        usuario = new Usuario(rootView.getContext(), idUsuario);

        TextView tvTitulo = (TextView) rootView.findViewById(R.id.tvTitulo);
        tvTitulo.setText("Modificar Datos");

        etNombre.setText(usuario.getNombre());
        etApellido.setText(usuario.getApellido());
        etUsuario.setText(usuario.getUsuario());
        etClave.setText(usuario.getClave());

        return  rootView;

    }

    public int recuperarIdUsuario(){
        BufferedReader fin = null;
        String token;
        try {
            fin = new BufferedReader(
                    new InputStreamReader(
                            getActivity().openFileInput(Contantes.TOKEN)));
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.modificar_datos,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnModificarDatos:
                actualizarDatos();
        }

        return true;
    }

    private void actualizarDatos() {
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String user = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        Boolean mismoUsuario = false;
        if(user.compareTo(usuario.getUsuario()) == 0){
            mismoUsuario = true;
        }
        if(controlarCampos(nombre,apellido,user,clave)){
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setUsuario(user);
            usuario.setClave(clave);

            if(mismoUsuario){
                usuario.modificacion();
                Toast.makeText(rootView.getContext(), getString(R.string.MODIFICACION_CORRECTA), Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,new fm_listarMedicamentos()).commit();
            }else if (!usuario.existeUsuario()) {
                usuario.modificacion();
                Toast.makeText(rootView.getContext(), getString(R.string.MODIFICACION_CORRECTA), Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new fm_listarMedicamentos()).commit();
            } else {
                Toast.makeText(rootView.getContext(), getString(R.string.USUARIO_EXISTENTE), Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean controlarCampos(String nombre, String apellido, String usuario, String clave) {
        if(nombre.isEmpty()) {
            Toast.makeText(rootView.getContext(),R.string.FALTA_NOMBRE,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(apellido.isEmpty()) {
            Toast.makeText(rootView.getContext(),R.string.FALTA_APELLIDO,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(usuario.isEmpty()) {
            Toast.makeText(rootView.getContext(),R.string.FALTA_USUARIO,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(clave.isEmpty()) {
            Toast.makeText(rootView.getContext(),R.string.FALTA_CLAVE,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
