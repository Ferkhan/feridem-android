package com.feridem.android.interfazdatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseUsuarios extends GestorBaseDatos {
    Context contexto;

    public BaseUsuarios(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

    /**
     *
     * @param nombre
     * @param correo
     * @param celular
     * @param contrasenia
     * @return returna el id en un long
     */
    public long insertarUsuario(String nombre, String correo, String celular, String contrasenia) {
        long id = 0;
        try{
            GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
            SQLiteDatabase baseDatos = gestorBaseDatos.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("nombre", nombre);
            valores.put("correo", correo);
            valores.put("celular", celular);
            valores.put("contrasenia", contrasenia);
            id = baseDatos.insert(TABLA_USUARIOS, null, valores);
        } catch (Exception e) {
            Log.i("mensaje error", e.toString());
        }
        return id;
    }

    public ArrayList<Usuarios> leerUsuarios() {
        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
        SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();

        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        Usuarios usuario;
        Cursor cursorUsuarios;

        cursorUsuarios = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLA_USUARIOS, null);

        if (cursorUsuarios.moveToFirst()) {
            do {
                usuario = new Usuarios();
                usuario.setId(cursorUsuarios.getInt(0));
                usuario.setNombre(cursorUsuarios.getString(1));
                usuario.setCorreo(cursorUsuarios.getString(2));
                usuario.setCelular(cursorUsuarios.getString(3));
                usuario.setContrasenia(cursorUsuarios.getString(4));
                listaUsuarios.add(usuario);
            } while (cursorUsuarios.moveToNext());
        }

        cursorUsuarios.close();

        return listaUsuarios;
    }
}
