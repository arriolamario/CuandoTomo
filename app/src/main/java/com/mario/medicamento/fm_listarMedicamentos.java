package com.mario.medicamento;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mario.medicamento.Clase.Medicamento;
import com.mario.medicamento.Clase.Usuario;
import com.mario.medicamento.Clases.ListMedicamentoAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Mario on 09/02/2016.
 */

//Lista de medicamentos
public class fm_listarMedicamentos extends Fragment {
    private final String CLASE = "fm_listarMedicamento";
    View rootView;
    ListView listView;
    ArrayList<Medicamento> listMed;
    ArrayList listaMedicamentos;
    Usuario usuario;
    public fm_listarMedicamentos() {
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fm_listar_medicamentos,container,false);
        setHasOptionsMenu(true);

        listView = (ListView) rootView.findViewById(R.id.lvListaMed);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Medicamento medicamento = (Medicamento) listaMedicamentos.get(position);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new fm_modificarMedicamento(medicamento)).commit();
                getActivity().setTitle("Modificar");
                return true;
            }
        });

        if(cargarMedicamentos()){
            if (usuario.getNotificacion() ==  1) {
                rootView.getContext().startService(new Intent(rootView.getContext(), ServiceMedicamento.class));
            }
            else{
                rootView.getContext().stopService(new Intent(rootView.getContext(), ServiceMedicamento.class));
            }
        }


        return rootView;


    }


    public boolean cargarMedicamentos(){
        int id = recuperarIdUsuario();
        if(id == -1) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new fm_listaVacia2()).commit();
            return false;
        }

        usuario = new Usuario(rootView.getContext(),id);
        listaMedicamentos = usuario.getListaMedicamentos();

        if(listaMedicamentos.size() != 0) {

            listView.setAdapter(new ListMedicamentoAdapter(getActivity(), listaMedicamentos));
        }
        else{
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new fm_listaVacia2()).commit();
            return false;
        }

        listMed = listaMedicamentos;

        return true;
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

        inflater.inflate(R.menu.listar_medicamentos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnActualizar:
                cargarMedicamentos();
                break;
            case R.id.btnStop:
                notificaciones();
                break;
        }

        return true;
    }

    private void notificaciones() {
        boolean corriendo = isMyServiceRunning(ServiceMedicamento.class);

        if(corriendo){
            rootView.getContext().stopService(new Intent(rootView.getContext(),ServiceMedicamento.class));
            Toast.makeText(rootView.getContext(), getString(R.string.STOP), Toast.LENGTH_SHORT).show();
            usuario.setNotificacion(0);
            usuario.modificacion();
        }else{
            usuario.setNotificacion(1);
            usuario.modificacion();
            Toast.makeText(rootView.getContext(), getString(R.string.ACTIVAS), Toast.LENGTH_SHORT).show();
            rootView.getContext().startService(new Intent(rootView.getContext(), ServiceMedicamento.class));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) rootView.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
