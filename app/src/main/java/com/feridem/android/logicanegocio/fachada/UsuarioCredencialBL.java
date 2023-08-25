package com.feridem.android.logicanegocio.fachada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.basedatos.GestorBaseDatos;
import com.feridem.android.logicanegocio.entidades.UsuarioCredencial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UsuarioCredencialBL extends GestorBaseDatos {
    private Context contexto;
    private ArrayList<UsuarioCredencial> listaCredenciales;

    public UsuarioCredencialBL(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

    public long insertarCredencial(int idUsuario, String contrasena) {
        long id = 0;

        try{
            GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
            SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();
            ContentValues valoresInsertar = new ContentValues();

            valoresInsertar.put("IdUsuario", idUsuario);
            valoresInsertar.put("Contrasena", contrasena);
            valoresInsertar.put("Estado", 1);
            id = sqLiteDatabase.insert(TABLA_USUARIO_CREDENCIAL, null, valoresInsertar);
        } catch (Exception e) {
            Log.i("mensaje feridem", e.toString());
        }
        Log.i("mensaje feridem", String.valueOf(id));

        return id;
    }

    public ArrayList<UsuarioCredencial> leerCredenciales() {
        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
        SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();
        Cursor cursorCredenciales = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLA_USUARIO_CREDENCIAL, null);

        listaCredenciales = new ArrayList<>();
        UsuarioCredencial usuarioCredencial;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (cursorCredenciales.moveToFirst()) {
            do {
                usuarioCredencial = new UsuarioCredencial();
                usuarioCredencial.setId(cursorCredenciales.getInt(0));
                usuarioCredencial.setContrasena(cursorCredenciales.getString(1));
                usuarioCredencial.setEstado(cursorCredenciales.getInt(2));
                try {
                    usuarioCredencial.setFechaIngreso(formatoFecha.parse(cursorCredenciales.getString(3)));
                    usuarioCredencial.setFechaModificacion(formatoFecha.parse(cursorCredenciales.getString(4)));
                } catch (ParseException e) {Log.i("mensaje feridem", e.getMessage());}

                listaCredenciales.add(usuarioCredencial);
            } while (cursorCredenciales.moveToNext());
        }
        cursorCredenciales.close();

        return listaCredenciales;
    }

}
