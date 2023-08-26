package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

import java.util.Date;

public class RegistroSesionDAC extends GestorBaseDatos {

    public RegistroSesionDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistrosActivos() throws AppException {
        String consultaSQL =  " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = Exito ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        return null;
    }

    public Cursor leerRegistroConectado() throws AppException {
        String consultaSQL =  " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = Exito "
                            + " AND   EstadoSesion = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    public long insertarRegistro(int idUsuario, String resultadoIngreso, int estadoSesion, Date fechaIngreso, Date fechaCierre) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdUsuario", idUsuario);
        valoresContenido.put("ResultadoIngreso", resultadoIngreso);
        valoresContenido.put("EstadoSesion", estadoSesion);
        valoresContenido.put("FechaIngreso", String.valueOf(fechaIngreso));
        valoresContenido.put("FechaCierre", String.valueOf(fechaCierre));
        return getWritableDatabase().insert(TABLA_REGISTRO_SESION, null, valoresContenido);
    }
}
