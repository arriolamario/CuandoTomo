package com.mario.medicamento;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mario.medicamento.Clase.Medicamento;
import com.mario.medicamento.Clase.Usuario;
import com.mario.medicamento.Clases.DrawerItem;
import com.mario.medicamento.Clases.DrawerListAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MenuActivity extends ActionBarActivity {

    String[] tagTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;


    Medicamento medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Obtener arreglo de strings desde los recursos
        tagTitles = getResources().getStringArray(R.array.Tags);
        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Obtener listview
        drawerList = (ListView) findViewById(R.id.left_drawer);



        //Nueva lista de drawer items
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(tagTitles[0], R.drawable.lista)); //0
        items.add(new DrawerItem(tagTitles[1], R.drawable.medicina));//1
        items.add(new DrawerItem(tagTitles[2], R.drawable.usuario));//2
        items.add(new DrawerItem(tagTitles[3], R.drawable.salida_1));//3


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });



        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new fm_listarMedicamentos()).commit();

        drawerList.setItemChecked(0, true);
        setTitle(tagTitles[0]);
        drawerLayout.closeDrawer(drawerList);


        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,null,R.string.drawer_open,R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new fm_listarMedicamentos();
                break;
            case 1:
                fragment = new fm_agregarMedicamento();
                break;
            case 2:
                fragment = new fm_DatosPersonales();
                break;
            case 3:
                fragment = new fm_salir();
                break;
            default:
                fragment = new fm_listarMedicamentos();
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);

    }


    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {

    }


}



