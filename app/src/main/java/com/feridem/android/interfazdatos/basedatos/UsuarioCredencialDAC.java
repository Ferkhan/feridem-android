package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

public class UsuarioCredencialDAC extends GestorBaseDatos {
    public UsuarioCredencialDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistros() throws AppException {
        consultaSQL = " SELECT IdUsuario, Contrasena, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_USUARIO_CREDENCIAL
                + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdUsuario, Contrasena, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_USUARIO_CREDENCIAL
                + " WHERE Estado = 1 "
                + " AND IdUsuario = ?";
        String[] valores = new String[] {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    public long insertarRegistro(int idUsuario, String contrasena) throws AppException {
        try{
            valoresContenido = new ContentValues();
            valoresContenido.put("IdUsuario", idUsuario);
            valoresContenido.put("Contrasena", contrasena);
            return getWritableDatabase().insert(TABLA_USUARIO_CREDENCIAL, null, valoresContenido);
        } catch (SQLException error) {
            throw new AppException(error, getClass(), "insertarRegistro()");
        }
    }
}
