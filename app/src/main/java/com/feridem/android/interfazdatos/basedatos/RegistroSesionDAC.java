package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;


public class RegistroSesionDAC extends GestorBaseDatos {

    public RegistroSesionDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistrosExito() throws AppException {
        String consultaSQL =  " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = Exito ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                    + " FROM " + TABLA_REGISTRO_SESION
                    + " WHERE IdRegistroSesion = ? ";
        String[] valores = new String[]{String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    public Cursor leerIdUsuarioConectado() throws AppException {
        String consultaSQL =  " SELECT IdUsuario "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = 'OK' "
                            + " AND   EstadoSesion = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    public Cursor leerRegistroConectado() throws AppException {
        String consultaSQL =  " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = 'OK' "
                            + " AND   EstadoSesion = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    public long insertarRegistro(int idUsuario, String resultadoIngreso, int estadoSesion) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdUsuario", idUsuario);
        valoresContenido.put("ResultadoIngreso", resultadoIngreso);
        valoresContenido.put("EstadoSesion", estadoSesion);
        return getWritableDatabase().insert(TABLA_REGISTRO_SESION, null, valoresContenido);
    }

    public int actualizarConexion(int idRegistro, String fechaCierre) {
        String[] valores = new String[] {String.valueOf(idRegistro)};
        valoresContenido = new ContentValues();
        valoresContenido.put("EstadoSesion", 0);
        valoresContenido.put("FechaCierre", fechaCierre);
        return getWritableDatabase().update(TABLA_REGISTRO_SESION, valoresContenido, "IdRegistroSesion = ?", valores);
    }
}
