package com.mario.medicamento;

import com.mario.medicamento.Clase.Usuario;

/**
 * Created by Mario on 18/06/2016.
 */
public class Contantes {
    public static final String NOTIFICACION = "notificacion.txt";
    private static String IP = "http://10.75.60.35";
    private static String PUERTO = ":3000";

    //DEVUELVE EL TOKEN SI SE LOGEA CORRECTAMENTE
    public static final String LOGIN = IP + PUERTO + "/appPublic/login";

    //ABM USUARIO
    public static final String INSERT_CUENTA = IP + PUERTO + "/apiPublic/usuario/put";
    public static final String UPDATE_CUENTA = IP + PUERTO + "/api/usuario/update";
    public static final String LIST_CUENTAS = IP + PUERTO + "/apiPublic/usuario/list"; //get

    //ABM MEDICAMENTOS
    public static final String INSERT_MED = IP + PUERTO + "/api/medicamento/put";
    public static final String UPDATE_MED = IP + PUERTO + "/api/medicamento/update";
    public static final String DELETE_MED = IP + PUERTO + "/api/medicamento/delete";
    public static final String LIST_MED = IP + PUERTO + "/api/medicamento/list";

    public static String URL(Usuario u, String url){
        return url +"?usuario="+u.getUsuario()+"&clave="+u.getClave()+"&nombre="+u.getNombre()+"&apellido="+u.getApellido();
    }


    public static  final String TOKEN = "token.txt";
    public static  final String DIA = "dia";
    public static  final String HORA = "hora";
    public static  final String MINUTO = "minuto";
}
