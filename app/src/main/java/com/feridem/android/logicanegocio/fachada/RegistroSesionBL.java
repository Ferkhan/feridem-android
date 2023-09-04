package com.feridem.android.logicanegocio.fachada;

import android.content.Context;
import android.widget.Toast;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.RegistroSesionDAC;
import com.feridem.android.logicanegocio.entidades.RegistroSesion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RegistroSesionBL extends GestorBL {
    RegistroSesion registroSesion;
    private RegistroSesionDAC registroSesionDAC;

    public RegistroSesionBL(Context contexto) {
        super(contexto);
        registroSesionDAC = new RegistroSesionDAC(contexto);
        registroSesion = new RegistroSesion();
    }

    public List<RegistroSesion> obtenerRegistrosExito() throws AppException {
        List<RegistroSesion> listaRegistros = new ArrayList<>();
        cursorConsulta = registroSesionDAC.leerRegistrosExito();

        if (cursorConsulta.moveToFirst()) {
            do {
                registroSesion = new RegistroSesion();
                registroSesion.setId(cursorConsulta.getInt(0));
                registroSesion.setIdUsuario(cursorConsulta.getInt(1));
                registroSesion.setResultadoIngreso(cursorConsulta.getString(2));
                registroSesion.setEstadoSesion((cursorConsulta.getInt(3)));
                try {
                    registroSesion.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(4)));
                    registroSesion.setFechaCierre(formatoFechaHora.parse(cursorConsulta.getString(5)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaRegistros.add(registroSesion);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaRegistros;
    }

    public RegistroSesion obtenerUsuarioConectado() throws AppException {
        cursorConsulta = registroSesionDAC.leerRegistroConectado();

        if (cursorConsulta.moveToLast()) {
            registroSesion = new RegistroSesion();
            registroSesion.setId(cursorConsulta.getInt(0));
            registroSesion.setIdUsuario(cursorConsulta.getInt(1));
            registroSesion.setResultadoIngreso(cursorConsulta.getString(2));
            registroSesion.setEstadoSesion((cursorConsulta.getInt(3)));
            try {
                registroSesion.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(4)));
                registroSesion.setFechaCierre(formatoFechaHora.parse(cursorConsulta.getString(5)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerRegistroConectado()");
            }
        }

        return registroSesion;
    }

    public boolean desconectarUsuario() throws AppException {
        registroSesion = obtenerUsuarioConectado();
        int idRegistroActualizado = registroSesionDAC.actualizarConexion(registroSesion.getId());
        if (idRegistroActualizado > 0) {
            Toast.makeText(contexto, "Sesión cerrada con éxito", Toast.LENGTH_SHORT);
            return true;
        } else {
            Toast.makeText(contexto, "Fallo al cerrar sesión", Toast.LENGTH_SHORT);
            return false;
        }
    }

    public boolean conectarUsuario(int idUsuario, String resultadoIngreso, int estadoSesion) throws AppException {
        long id = registroSesionDAC.insertarRegistro(idUsuario, resultadoIngreso, estadoSesion);
        return id > 0;
    }

}
