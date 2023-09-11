package com.feridem.android.business_logic.managers;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.data_access.UsuarioCredencialDAC;
import com.feridem.android.business_logic.entities.UsuarioCredencial;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponde a la logica de negocios correspondiente a Usuario Credencial.
 */
public class UsuarioCredencialBL extends GestorBL {
    private UsuarioCredencialDAC usuarioCredencialDAC;

    public UsuarioCredencialBL(Context contexto) {
        super(contexto);
        usuarioCredencialDAC = new UsuarioCredencialDAC(contexto);
    }

    /**
     * obtenerRegistrosActivos:Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos.
     * De esta manera, obtiene la informacion y la envia a la entidad usuarioCredencial.
     * @return listaCredenciales: Representa a las credenciales de los usuarios que estan activos.
     * @throws AppException
     */
    public List<UsuarioCredencial> obtenerRegistrosActivos() throws AppException {
        UsuarioCredencial usuarioCredencial;
        List<UsuarioCredencial> listaCredenciales = new ArrayList<>();
        cursorConsulta = usuarioCredencialDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                usuarioCredencial = new UsuarioCredencial();
                usuarioCredencial.setId(cursorConsulta.getInt(0));
                usuarioCredencial.setContrasena(cursorConsulta.getString(1));
                usuarioCredencial.setEstado(cursorConsulta.getInt(2));
                try {
                    usuarioCredencial.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(3)));
                    usuarioCredencial.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(4)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaCredenciales.add(usuarioCredencial);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaCredenciales;
    }

    /**
     * obtenerPorId: Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos de una credencial en especifico.
     *  De esta manera, obtiene la informacion y la envia a la entidad usuarioCredencial.
     * @param idRegistro: Representa al Id del registro.
     * @return usuarioCredencial en caso de encontrarse, sino retorna null
     * @throws AppException
     */
    public UsuarioCredencial obtenerPorId(int idRegistro) throws AppException {
        UsuarioCredencial usuarioCredencial = new UsuarioCredencial();
        cursorConsulta = usuarioCredencialDAC.leerPorId(idRegistro);

        if (cursorConsulta.moveToFirst()) {
            usuarioCredencial = new UsuarioCredencial();
            usuarioCredencial.setId(cursorConsulta.getInt(0));
            usuarioCredencial.setContrasena(cursorConsulta.getString(1));
            usuarioCredencial.setEstado(cursorConsulta.getInt(2));
            try {
                usuarioCredencial.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(3)));
                usuarioCredencial.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(4)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerPorId()");
            }
        }

        return usuarioCredencial;
    }

    /**
     * insertarCredencial: Se encarga de insertar una credencial en la base de datos.
     * @param idUsuario: Representa al Id de las credenciales
     * @param contrasena: Representa a la contraseña del usuario.
     * @return long
     * @throws AppException
     */
    public long insertarCredencial(int idUsuario, String contrasena) throws AppException {
        return usuarioCredencialDAC.insertarRegistro(idUsuario, contrasena);
    }
}
