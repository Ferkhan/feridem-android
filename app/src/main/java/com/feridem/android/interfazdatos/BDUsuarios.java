package com.feridem.android.interfazdatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class BDUsuarios extends GestorBaseDatos {
    Context contexto;

    public BDUsuarios(@Nullable Context contexto) {
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
            valores.put("telefono", celular);
            valores.put("contrasenia", contrasenia);
            id = baseDatos.insert(TABLA_USUARIOS, null, valores);
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }
}
