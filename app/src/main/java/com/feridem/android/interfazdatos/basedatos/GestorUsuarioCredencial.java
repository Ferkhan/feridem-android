package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.modeloentidad.Usuario;
import com.feridem.android.interfazdatos.modeloentidad.UsuarioCredencial;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GestorUsuarioCredencial extends GestorBaseDatos{
    private Context contexto;
    private ArrayList<UsuarioCredencial> listaCredenciales;

    public GestorUsuarioCredencial(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

//    public long insertarCredencial(int id) {
//
//    }
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
                    usuarioCredencial.setFechaIngreso((Date) formatoFecha.parse(cursorCredenciales.getString(6)));
                    usuarioCredencial.setFechaModificacion((Date) formatoFecha.parse(cursorCredenciales.getString(7)));
                } catch (ParseException e) {e.printStackTrace();}

                listaCredenciales.add(usuarioCredencial);
            } while (cursorCredenciales.moveToNext());
        }
        cursorCredenciales.close();

        return listaCredenciales;
    }

}
