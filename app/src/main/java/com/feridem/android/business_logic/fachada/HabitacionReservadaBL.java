package com.feridem.android.business_logic.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.data_access.HabitacionReservadaDAC;
import com.feridem.android.business_logic.entidades.HabitacionReservada;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponde a la lógica de negocios de habitación reservada.
 */
public class HabitacionReservadaBL extends GestorBL {
    private HabitacionReservadaDAC habitacionReservadaDAC;
    private RegistroSesionBL registroSesionBL;

    public HabitacionReservadaBL(Context contexto) {
        super(contexto);
        habitacionReservadaDAC = new HabitacionReservadaDAC(contexto);
        registroSesionBL = new RegistroSesionBL(contexto);
    }

    /**
     * obtenerRegistrosActivos:Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos.
     *  De esta manera, obtiene la información y la envía a la entidad habitacionReservada.
     * @return listaHabitaciones: Representa el conjunto de habitaciones reservadas.
     * @throws AppException
     */
    public List<HabitacionReservada> obtenerRegistrosActivos() throws AppException {
        HabitacionReservada habitacionReservada;
        List<HabitacionReservada> listaHabitaciones = new ArrayList<>();
        cursorConsulta = habitacionReservadaDAC.leerRegistrosUsuario(registroSesionBL.obtenerIdUsuarioConectado());

        if (cursorConsulta.moveToFirst()) {
            do {
                habitacionReservada = new HabitacionReservada();
                habitacionReservada.setId(cursorConsulta.getInt(0));
                habitacionReservada.setIdHabitacion(cursorConsulta.getInt(1));
                habitacionReservada.setIdUsuario(cursorConsulta.getInt(2));
                habitacionReservada.setFechaEntrada(cursorConsulta.getString(3));
                habitacionReservada.setFechaSalida(cursorConsulta.getString(4));
                habitacionReservada.setTotalNoches(cursorConsulta.getInt(5));
                habitacionReservada.setPrecioNoche(cursorConsulta.getDouble(6));
                habitacionReservada.setPrecioTotal(cursorConsulta.getDouble(7));
                habitacionReservada.setCodigoQR(cursorConsulta.getString(8));
                habitacionReservada.setEstado(cursorConsulta.getInt(9));
                try {
                    habitacionReservada.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(10)));
                    habitacionReservada.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(11)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaHabitaciones.add(habitacionReservada);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaHabitaciones;
    }

    /**
     * obtenerPorId: Se encarga de llamar al metodo leerPorId en la DAC, para poder obtener la información de una habitación reservada específica.
     * De esta manera, enviía dicha informacion a la entidad habitacionReservada.
     * @param idReservacion
     * @return habitacionReservada
     * @throws AppException
     */
    public HabitacionReservada obtenerPorId(int idReservacion) throws AppException {
        HabitacionReservada habitacionReservada = new HabitacionReservada();
        cursorConsulta = habitacionReservadaDAC.leerPorId(idReservacion);

        if (cursorConsulta.moveToFirst()) {
            habitacionReservada.setId(cursorConsulta.getInt(0));
            habitacionReservada.setIdHabitacion(cursorConsulta.getInt(1));
            habitacionReservada.setIdUsuario(cursorConsulta.getInt(2));
            habitacionReservada.setFechaEntrada(cursorConsulta.getString(3));
            habitacionReservada.setFechaSalida(cursorConsulta.getString(4));
            habitacionReservada.setTotalNoches(cursorConsulta.getInt(5));
            habitacionReservada.setPrecioNoche(cursorConsulta.getDouble(6));
            habitacionReservada.setPrecioTotal(cursorConsulta.getDouble(7));
            habitacionReservada.setCodigoQR(cursorConsulta.getString(8));
            habitacionReservada.setEstado(cursorConsulta.getInt(9));
            try {
                habitacionReservada.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(10)));
                habitacionReservada.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(11)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
            }
        }

        cursorConsulta.close();
        return habitacionReservada;
    }

    /**
     * ingresarRegistro: Permite ingresar un nuevo registro sobre una habitación que ha sido rentada, para esto llama al metodo
     * insertarRegistro que esta en la DAC.
     * @param idHabitacion: Representa al Id de la habitacion que se ha rentado.
     * @param idUsuario: Representa al usuario que rentó la habitación.
     * @param fechaEntrada: Representa la fecha de inicio, en la que se rentó la habitación.
     * @param fechaSalida: Representa la fecha fianl, en la que se rentó la habitación.
     * @param totalNoches: Representa el numero de noches que hay entre la fecha inicio y la fecha final.
     * @param precioNoche: Representa el precio de la noche, de acuerdo a la habitación que se ha rentado.
     * @param precioTotal: Representa el precio total, de acuerdo al numero de noches.
     * @param codigoQR: Representa al Texto único, para el código QR.
     * @return: long: Representa el identificador del registro que ha sido insertado.
     */
    public long ingresarRegistro(int idHabitacion, int idUsuario, String fechaEntrada, String fechaSalida, int totalNoches, double precioNoche, double precioTotal, String codigoQR) {
        return habitacionReservadaDAC.insertarRegistro(idHabitacion, idUsuario, fechaEntrada, fechaSalida, totalNoches, precioNoche, precioTotal, codigoQR);

    }

}