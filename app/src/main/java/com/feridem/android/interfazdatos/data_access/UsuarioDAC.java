package com.feridem.android.interfazdatos.data_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

/**
 * Este es el DAC para usuario.
 */
public class UsuarioDAC extends GestorBaseDatos {

    public UsuarioDAC(@Nullable Context contexto) {
        super(contexto);
    }

    /**
     * leerRegistros: Se encarga de leer los registros activos en la tabla "Usuario"
     * @return cursorConsulta:Representa la tabla de registros activos, obtenidos a partir del query.
     * @throws AppException
     */
    @Override
    public Cursor leerRegistros() throws AppException {
        consultaSQL = " SELECT IdUsuario, IdRol, Nombre, Correo, Celular, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_USUARIO
                    + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerPorId: Se encarga de leer los registros activos en la tabla "Usuario" a partir de un Id de usuario específico.
     * @param idRegistro: Representa al Id del usuario.
     * @return cursorConsulta:Representa la tabla de registros activos, obtenidos a partir del query.
     * @throws AppException
     */
    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdUsuario, IdRol, Nombre, Correo, Celular, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_USUARIO
                    + " WHERE Estado = 1 "
                    + " AND IdUsuario = ? ";
        String[] valores = new String[]{String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    /**
     * insertarRegistro: Se encarga de insertar un nuevo reigstor en la tabla "Usuario", para esto toma como parámetros:
     *                  IdRol, nombre, correo, celular.
     * @param IdRol: Representa el Id del Rol.
     * @param nombre: Representa el nombre del usuario
     * @param correo: Representa al correo del usuario:
     * @param celular: Representa el número celular del usuario.
     * @return long: Representa el identificador del registro que ha sido insertado.
     */
    public long insertarRegistro(int IdRol, String nombre, String correo, String celular) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdRol", IdRol);
        valoresContenido.put("Nombre", nombre);
        valoresContenido.put("Correo", correo);
        valoresContenido.put("Celular", celular);
        return getWritableDatabase().insert(TABLA_USUARIO, null, valoresContenido);
    }

    /**
     * actualizarRegistro: Se encarga de actualizar los datos de un Usuario en la respectiva tabla.
     * @param IdRol: Representa el Id del rol
     * @param nombre: Representa el nombre del usuario
     * @param correo: Representa el correo del usuario
     * @param celular: Representa el numero celular del usuario.
     * @return long: Representa el número de registros que se han actializado en la tabla.
     */
    public long actualizarRegistro(int IdRol, String nombre, String correo, String celular) {
        String[] valores = new String[] {String.valueOf(IdRol)};
        valoresContenido = new ContentValues();
        valoresContenido.put("Nombre", nombre);
        valoresContenido.put("Correo", correo);
        valoresContenido.put("Celular", celular);
        return getWritableDatabase().update(TABLA_USUARIO,valoresContenido,"IdRol= ?",valores);
    }
}
