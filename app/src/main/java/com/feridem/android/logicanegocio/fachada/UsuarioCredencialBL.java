package com.feridem.android.logicanegocio.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.UsuarioCredencialDAC;
import com.feridem.android.logicanegocio.entidades.UsuarioCredencial;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCredencialBL extends GestorBL {
    private UsuarioCredencialDAC usuarioCredencialDAC;

    public UsuarioCredencialBL(Context contexto) {
        super(contexto);
        usuarioCredencialDAC = new UsuarioCredencialDAC(contexto);
    }


    public List<UsuarioCredencial> obtenerRegistrosActivos() throws AppException {
        UsuarioCredencial usuarioCredencial;
        List<UsuarioCredencial> listaCredenciales = new ArrayList<>();
        cursorConsulta = usuarioCredencialDAC.leerRegistrosActivos();

        if (cursorConsulta.moveToFirst()) {
            do {
                usuarioCredencial = new UsuarioCredencial();
                usuarioCredencial.setId(cursorConsulta.getInt(0));
                usuarioCredencial.setContrasena(cursorConsulta.getString(1));
                usuarioCredencial.setEstado(cursorConsulta.getInt(2));
                try {
                    usuarioCredencial.setFechaRegistro(formatoFecha.parse(cursorConsulta.getString(3)));
                    usuarioCredencial.setFechaModificacion(formatoFecha.parse(cursorConsulta.getString(4)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaCredenciales.add(usuarioCredencial);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaCredenciales;
    }

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


    public long insertarCredencial(int idUsuario, String contrasena) throws AppException {
        return usuarioCredencialDAC.insertarRegistro(idUsuario, contrasena);
    }
}
