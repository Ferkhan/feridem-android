package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

public class UsuarioDAC extends GestorBaseDatos {

    public UsuarioDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistros() throws AppException {
        consultaSQL = " SELECT IdUsuario, IdRol, Nombre, Correo, Celular, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_USUARIO
                    + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdUsuario, IdRol, Nombre, Correo, Celular, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_USUARIO
                    + " WHERE Estado = 1 "
                    + " AND IdUsuario = ? ";
        String[] valores = new String[]{String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    public long insertarRegistro(int IdRol, String nombre, String correo, String celular) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdRol", IdRol);
        valoresContenido.put("Nombre", nombre);
        valoresContenido.put("Correo", correo);
        valoresContenido.put("Celular", celular);
        return getWritableDatabase().insert(TABLA_USUARIO, null, valoresContenido);
    }
    public long actualizarRegistro(int IdRol, String nombre, String correo, String celular) {
        String[] valores = new String[] {String.valueOf(IdRol)};
        valoresContenido = new ContentValues();
        valoresContenido.put("Nombre", nombre);
        valoresContenido.put("Correo", correo);
        valoresContenido.put("Celular", celular);
        return getWritableDatabase().update(TABLA_USUARIO,valoresContenido,"IdRol= ?",valores);
    }
}
