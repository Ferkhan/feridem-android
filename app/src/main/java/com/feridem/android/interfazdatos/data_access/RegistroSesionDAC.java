package com.feridem.android.interfazdatos.data_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

/**
 * Este es el DAC para el registro de sesion.
 */
public class RegistroSesionDAC extends GestorBaseDatos {

    public RegistroSesionDAC(@Nullable Context contexto) {
        super(contexto);
    }

    /**
     * leerRegistros: Se encarga de leer los registros de la Tabla, "Registro Sesion".
     * @return cursorConsulta: Es la tabla de registros con un resultado de ingreso exitoso, obtenida a partir de la consulta del query.
     * @throws AppException
     */
    @Override
    public Cursor leerRegistros() throws AppException {
        consultaSQL =  " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = 'OK' ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerPorId: Se encarga de leer los registors de la tabla  "Registro Sesion", a partir del Id del registro de la sesión.
     * @param idRegistro: Representa el Id del registro de la sesión.
     * @return cursorConsulta: Es la tabla de registros , obtenida  a partir de la consulta del query y un Id de registro sesion específico.
     * @throws AppException
     */
    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                    + " FROM " + TABLA_REGISTRO_SESION
                    + " WHERE IdRegistroSesion = ? ";
        String[] valores = new String[]{String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    /**
     * leerIdUsuarioConectado: Se encarga de leer el usuario que se encuentra con un registro en "OK" y un estado de sesion "activo".
     * @return cursorConsulta: Es la tabla de registros con un resultado de ingreso exitoso y un estado de sesión activo, obtenida a partir de la consulta del query.
     * @throws AppException
     */
    public Cursor leerIdUsuarioConectado() throws AppException {
        String consultaSQL =  " SELECT IdUsuario "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = 'OK' "
                            + " AND   EstadoSesion = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerRegistroConectado: Se encarga de leer los registros que han tenido un resultado de ingresio exitoso.
     * @return cursorConsulta: Es la tabla de registros con un resultado de ingreso exitoso y un estado de sesión activo, obtenida a partir de la consulta del query.
     * @throws AppException
     */
    public Cursor leerRegistroConectado() throws AppException {
        String consultaSQL =  " SELECT IdRegistroSesion, IdUsuario, ResultadoIngreso, EstadoSesion, FechaIngreso, FechaCierre "
                            + " FROM " + TABLA_REGISTRO_SESION
                            + " WHERE ResultadoIngreso = 'OK' "
                            + " AND   EstadoSesion = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * insertarRegistro: Se encarga de insertar nuevos registros en la tabla "Registro Sesion", para esto
     *                  toma como parámetros, idUsuario,resultadoIngreso,estadoSesion.
     * @param idUsuario: Representa el Id de usuario que ha realizado un registro.
     * @param resultadoIngreso: Representa el resultado de ingreso, ya sea exitoso o fallido.
     * @param estadoSesion: Representa el estado de la sesión (activo o inactivo).
     * @return long:  identificador del nuevo registro que se ha insertado en la tabla.
     */
    public long insertarRegistro(int idUsuario, String resultadoIngreso, int estadoSesion) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdUsuario", idUsuario);
        valoresContenido.put("ResultadoIngreso", resultadoIngreso);
        valoresContenido.put("EstadoSesion", estadoSesion);
        return getWritableDatabase().insert(TABLA_REGISTRO_SESION, null, valoresContenido);
    }

    /**
     * actualizarConexion: Se encarga de cambiar el estado de sesión de un usuario, toma como parametros el Id del registro de sesión
     *                      y la fecha de cierre.
     * @param idRegistro: Representa al Id del registro de la sesión
     * @param fechaCierre: Representa a la fecha en la que se desconecto al usuario.
     * @return int: Retorna el número de registros que se han actializado en la tabla.
     */
    public int actualizarConexion(int idRegistro, String fechaCierre) {
        String[] valores = new String[] {String.valueOf(idRegistro)};
        valoresContenido = new ContentValues();
        valoresContenido.put("EstadoSesion",0);
        valoresContenido.put("FechaCierre", fechaCierre);
        return getWritableDatabase().update(TABLA_REGISTRO_SESION, valoresContenido, "IdRegistroSesion = ?", valores);
    }

}
