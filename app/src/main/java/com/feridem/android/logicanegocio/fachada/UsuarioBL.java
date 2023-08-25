package com.feridem.android.logicanegocio.fachada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.basedatos.GestorBaseDatos;
import com.feridem.android.logicanegocio.entidades.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UsuarioBL extends GestorBaseDatos {
    private Context contexto;
    private ArrayList<Usuario> listaUsuarios;

    public UsuarioBL(@Nullable Context contexto) {
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
            SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();
            ContentValues valoresInsertar = new ContentValues();

            valoresInsertar.put("IdRol", 1);
            valoresInsertar.put("Nombre", nombre);
            valoresInsertar.put("Correo", correo);
            valoresInsertar.put("Celular", celular);
            valoresInsertar.put("Estado", 1);
            id = sqLiteDatabase.insert(TABLA_USUARIO, null, valoresInsertar);
        } catch (Exception e) {
            Log.i("mensaje feridem", e.toString());
        }
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
                    usuario.setFechaRegistro(formatoFecha.parse(cursorUsuarios.getString(6)));
                    usuario.setFechaModificacion(formatoFecha.parse(cursorUsuarios.getString(7)));
                } catch (ParseException e) {Log.i("mensaje feridem", "otro error");}
                listaUsuarios.add(usuario);
            } while (cursorUsuarios.moveToNext());
        }
        cursorUsuarios.close();

        return listaUsuarios;
    }
}
