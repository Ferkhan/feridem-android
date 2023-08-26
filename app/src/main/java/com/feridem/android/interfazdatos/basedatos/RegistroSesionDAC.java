package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

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

}
