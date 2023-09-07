package com.feridem.android.business_logic.fachada;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.data_access.RegistroSesionDAC;
import com.feridem.android.business_logic.entidades.RegistroSesion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Corresponde a la lógica de neogocios para el registro de la sesión.
 */
public class RegistroSesionBL extends GestorBL {
    RegistroSesion registroSesion;
    private RegistroSesionDAC registroSesionDAC;

    public RegistroSesionBL(Context contexto) {
        super(contexto);
        registroSesionDAC = new RegistroSesionDAC(contexto);
        registroSesion = new RegistroSesion();
    }

    /**
     * obtenerRegistroExito: Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos.
     * De esta manera, obtiene la información y la envía a la entidad registroSesion.
     * @return listaRegistros: Representa al conjunto de registros exitosos.
     * @throws AppException
     */
    public List<RegistroSesion> obtenerRegistrosExito() throws AppException {
        List<RegistroSesion> listaRegistros = new ArrayList<>();
        cursorConsulta = registroSesionDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                registroSesion = new RegistroSesion();
                registroSesion.setId(cursorConsulta.getInt(0));
                registroSesion.setIdUsuario(cursorConsulta.getInt(1));
                registroSesion.setResultadoIngreso(cursorConsulta.getString(2));
                registroSesion.setEstadoSesion((cursorConsulta.getInt(3)));
                registroSesion.setFechaIngreso(cursorConsulta.getString(4));
                registroSesion.setFechaCierre(cursorConsulta.getString(5));
                listaRegistros.add(registroSesion);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaRegistros;
    }

    /**
     * obtenerIdUsuarioConectado: Se encarga de obtener el Id del usuario que se encuentra conectado
     * @return int: Id del usuario conectado.
     * @throws AppException
     */
    public int obtenerIdUsuarioConectado() throws AppException {
        cursorConsulta = registroSesionDAC.leerIdUsuarioConectado();

        if (cursorConsulta.moveToLast()) {
            return cursorConsulta.getInt(0);
        }
        return 0;
    }

    /**
     * obtenerRegistroConectado: Se encarga de obtener el registro de la sesion del usuario que esta conectado.
     * @return registroSesion: Registro del usuario que esta conectado.
     * @throws AppException
     */
    public RegistroSesion obtenerRegistroConectado() throws AppException {
        cursorConsulta = registroSesionDAC.leerRegistroConectado();
        registroSesion = new RegistroSesion();

        if (cursorConsulta.moveToLast()) {
            registroSesion.setId(cursorConsulta.getInt(0));
            registroSesion.setIdUsuario(cursorConsulta.getInt(1));
            registroSesion.setResultadoIngreso(cursorConsulta.getString(2));
            registroSesion.setEstadoSesion((cursorConsulta.getInt(3)));
            registroSesion.setFechaIngreso(cursorConsulta.getString(4));
            registroSesion.setFechaCierre(cursorConsulta.getString(5));
            Log.i("AppException", "Si hay un registro conectado");
        }

        return registroSesion;
    }

    /**
     * desconectarUsuario: Se encarga de cambiar el estado de sesión del usuario.
     * @return boolean: Retorna true si se ha desconectado con éxito y false si se ha generado algun error.
     * @throws AppException
     */
    public boolean desconectarUsuario() throws AppException {
        Date fecha = new Date();
        registroSesion=new RegistroSesion();
        registroSesion = obtenerRegistroConectado();
        if(registroSesion != null) {
            int idRegistroActualizado = registroSesionDAC.actualizarConexion(registroSesion.getId(), formatoFechaHora.format(fecha));
            if (idRegistroActualizado > 0) {
                Toast.makeText(contexto, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(contexto, "Fallo al cerrar sesión", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(contexto, "No hay usuario conectado", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * conectarUsuario: Se encarga de conectar a un usuario en la base de datos.
     * @param idUsuario: Representa el usuario a conectar
     * @param resultadoIngreso: Representa si el ingreso es exitoso o no.
     * @param estadoSesion: Representa si esta activo o no el usuario.
     * @return boolean
     * @throws AppException
     */
    public boolean conectarUsuario(int idUsuario, String resultadoIngreso, int estadoSesion) throws AppException {
        long id = registroSesionDAC.insertarRegistro(idUsuario, resultadoIngreso, estadoSesion);
        return id > 0;
    }



}
