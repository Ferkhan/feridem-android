package com.feridem.android.logicanegocio.fachada;

import android.content.Context;
import android.util.Log;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.UsuarioDAC;
import com.feridem.android.logicanegocio.entidades.Usuario;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioBL extends GestorBL{
    private Usuario usuario;
    private UsuarioDAC usuarioDAC;

    public UsuarioBL(Context contexto) {
        super(contexto);
        usuarioDAC = new UsuarioDAC(contexto);
        usuario = new Usuario();
    }

    public List<Usuario> obtenerRegistrosActivos() throws AppException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        cursorConsulta = usuarioDAC.leerRegistrosActivos();

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

    public long ingresarRegistro(int idRol, String nombre, String correo, String celular) {
        return usuarioDAC.insertarRegistro(idRol, nombre, correo, celular);
    }
}
