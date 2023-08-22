package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.modeloentidad.Usuario;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GestorUsuario extends GestorBaseDatos {
    private Context contexto;
    private ArrayList<Usuario> listaUsuarios;

    public GestorUsuario(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

    /**
     *
     * @param nombre
     * @param correo
     * @param celular
     * @return returna el id en un long
     */
    public long insertarUsuario(String nombre, String correo, String celular) {
        long id = 0;
        try{
            GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
            SQLiteDatabase baseDatos = gestorBaseDatos.getWritableDatabase();
            ContentValues valores = new ContentValues();
            Log.i("mensaje error", "empezando a emparejar");

            valores.put("Nombre", nombre);
            valores.put("Correo", correo);
            valores.put("Celular", celular);
            valores.put("Estado", 1);
            id = baseDatos.insert(TABLA_USUARIO, null, valores);
            Log.i("mensaje error", "ha terminado de emparejar");
        } catch (Exception e) {
            Log.i("mensaje error", e.toString());
        }
        Log.i("mensaje error", String.valueOf(id));
        return id;
    }

    public ArrayList<Usuario> leerUsuarios() {
        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
        SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();
        Cursor cursorUsuarios = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLA_USUARIO, null);

        listaUsuarios = new ArrayList<>();
        Usuario usuario;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (cursorUsuarios.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setId(cursorUsuarios.getInt(0));
                usuario.setIdRol(cursorUsuarios.getInt(1));
                usuario.setNombre(cursorUsuarios.getString(2));
                usuario.setCorreo(cursorUsuarios.getString(3));
                usuario.setCelular(cursorUsuarios.getString(4));
                usuario.setEstado(cursorUsuarios.getInt(5));
                try {
                    usuario.setFechaRegistro((Date) formatoFecha.parse(cursorUsuarios.getString(6)));
                    usuario.setFechaModificacion((Date) formatoFecha.parse(cursorUsuarios.getString(7)));
                } catch (ParseException e) {e.printStackTrace();}

                listaUsuarios.add(usuario);
            } while (cursorUsuarios.moveToNext());
        }
        cursorUsuarios.close();

        return listaUsuarios;
    }
}
