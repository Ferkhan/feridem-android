package com.feridem.android.logicanegocio.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.UsuarioDAC;
import com.feridem.android.logicanegocio.entidades.Usuario;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponde a la lógica de negocios correspondiente a usuario.
 */
public class UsuarioBL extends GestorBL{
    private Usuario usuario;
    private UsuarioDAC usuarioDAC;


    public UsuarioBL(Context contexto) {
        super(contexto);
        usuarioDAC = new UsuarioDAC(contexto);
        usuario = new Usuario();
    }

    /**
     * obtenerRegistrosActivos:Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos.
     * De esta manera, obtiene la información y la envía a la entidad Usuario.
     * @return listaUsuario: Representa el conjunto de usuarios activos.
     * @throws AppException
     */
    public List<Usuario> obtenerRegistrosActivos() throws AppException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        cursorConsulta = usuarioDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setId(cursorConsulta.getInt(0));
                usuario.setIdRol(cursorConsulta.getInt(1));
                usuario.setNombre(cursorConsulta.getString(2));
                usuario.setCorreo(cursorConsulta.getString(3));
                usuario.setCelular(cursorConsulta.getString(4));
                usuario.setEstado(cursorConsulta.getInt(5));
                try {
                    usuario.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(6)));
                    usuario.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(7)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaUsuarios.add(usuario);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaUsuarios;
    }

    /**
     * obtenerPorId:Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos de un usuario en específico.
     *      * De esta manera, obtiene la información y la envía a la entidad Usuario.
     * @param idUsuario: Representa al Id del usuario.
     * @return usuario
     * @throws AppException
     */
    public Usuario obtenerPorId(int idUsuario) throws AppException {
        cursorConsulta = usuarioDAC.leerPorId(idUsuario);

        if (cursorConsulta.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursorConsulta.getInt(0));
            usuario.setIdRol(cursorConsulta.getInt(1));
            usuario.setNombre(cursorConsulta.getString(2));
            usuario.setCorreo(cursorConsulta.getString(3));
            usuario.setCelular(cursorConsulta.getString(4));
            usuario.setEstado(cursorConsulta.getInt(5));
            try {
                usuario.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(6)));
                usuario.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(7)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerPorId()");
            }
        }

        return usuario;
    }

    /**
     * ingresarRegistro: Se encarga de insertar un usuario en la base de datos.
     * @param idRol: Representa el Id del rol
     * @param nombre: Representa el nombre del usuario
     * @param correo: Representa el correo del usuario
     * @param celular: Representa el numero celular del usuario.
     * @return long: Representa el identificador del registro ingresado
     */
    public long ingresarRegistro(int idRol, String nombre, String correo, String celular) {
        return usuarioDAC.insertarRegistro(idRol, nombre, correo, celular);
    }

    /**
     * actualizarRegistro: Se encarga de actualizar la informacion de un usuario en la base de datos.
     * @param idRol: Representa el Id del rol
     * @param nombre: Representa el nombre del usuario
     * @param correo: Representa el correo del usuario
     * @param celular: Representa el numero celular del usuario.
     * @return long: Representa el identificador del registro actualizado.
     */
    public long actualizarRegistro(int idRol, String nombre, String correo, String celular){
        return usuarioDAC.actualizarRegistro(idRol,  nombre,  correo, celular);
    }
}
