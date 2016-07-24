package com.mario.medicamento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.io.OutputStreamWriter;
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
        btnRegistrar = (Button) rootView.findViewById(R.id.btnRegistrar);

        btnRegistrar.setVisibility(View.INVISIBLE);
        int idUsuario = recuperarIdUsuario();

        usuario = new Usuario(rootView.getContext(), idUsuario);

        TextView tvTitulo = (TextView) rootView.findViewById(R.id.tvTitulo);
        tvTitulo.setText("Modificar Datos");

        etNombre.setText(usuario.getNombre());
        etApellido.setText(usuario.getApellido());
        etUsuario.setText(usuario.getUsuario());

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
                break;
            case R.id.btnEliminar:
                eliminar();
                break;
        }

        return true;
    }

    private void eliminar() {
        new Dialogo().show(getFragmentManager(),"EliminarUsuario");
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


    public class Dialogo extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
            builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    usuario.baja();
                    Salir();
                    startActivity(new Intent(rootView.getContext(),LoginActivity.class));
                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setTitle("Alerta").setMessage("¿Esta seguro que quiere eliminar el usuario?").setIcon(R.drawable.alerta);

            return builder.create();
        }
    }

    public void Salir(){
        // Abrir un fichero de salida privado a la aplicación
        OutputStreamWriter fout= null;
        try {
            fout = new OutputStreamWriter(getActivity().openFileOutput(Contantes.TOKEN, Context.MODE_PRIVATE));
            fout.write("");
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
    }


}
