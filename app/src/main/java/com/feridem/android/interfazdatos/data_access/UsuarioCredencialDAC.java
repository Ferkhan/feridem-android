package com.feridem.android.interfazdatos.data_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

/**
 * Este es el DAC para el usuario Credencial.
 */
public class UsuarioCredencialDAC extends GestorBaseDatos {
    public UsuarioCredencialDAC(@Nullable Context contexto) {
        super(contexto);
    }

    /**
     * leerRegistros: Se encarga de leer los registros de la tabla "Usuario Credencial" con un estado activo.
     * @return cursorConsulta: Es la tabla de registros  un estado  activo, obtenida a partir de la consulta del query.
     * @throws AppException
     */
    @Override
    public Cursor leerRegistros() throws AppException {
        consultaSQL = " SELECT IdUsuario, Contrasena, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_USUARIO_CREDENCIAL
                + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerPorId: Se encarga de leer los registros de la tabla "Usuario Credencial" que tienen un estado activo
     *          y un Id de usuario específico.
     * @param idRegistro: Representa el Id del usuario.
     * @return cursorConsulta: Es la tabla de registros con un estado activo y un Id de usuario específico.
     * @throws AppException
     */
    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdUsuario, Contrasena, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_USUARIO_CREDENCIAL
                + " WHERE Estado = 1 "
                + " AND IdUsuario = ?";
        String[] valores = new String[] {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    /**
     * insertarRegistro: Se encarga de insertar en la tabla "Usuario Credencial" nuevos registros
     *                  Toma como parametros el Id del usuario y la contraseña.
     * @param idUsuario: Representa el Id del usuario
     * @param contrasena: Representa la contraseña del usuario.
     * @return long: Representa el identificador del registro insertado.
     * @throws AppException
     */
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
