package com.feridem.android.logicanegocio.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.HabitacionDAC;
import com.feridem.android.logicanegocio.entidades.Habitacion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Correponde a la l+ógica de negocios de habitación.
 */
public class HabitacionBL extends GestorBL {
    private HabitacionDAC habitacionDAC;

    public HabitacionBL(Context contexto) {
        super(contexto);
        habitacionDAC = new HabitacionDAC(contexto);
    }

    /**
     * obtenerRegistrosActivos: Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos.
     * De esta manera, obtiene la información y la envía a la entidad habitación.
     * @return listaHabitaciones: Reprsenta a un conjunto de habitaciones.
     * @throws AppException
     */
    public List<Habitacion> obtenerRegistrosActivos() throws AppException {
        Habitacion habitacion;
        List<Habitacion> listaHabitaciones = new ArrayList<>();
        cursorConsulta = habitacionDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                habitacion = new Habitacion();
                habitacion.setId(cursorConsulta.getInt(0));
                habitacion.setIdHotel(cursorConsulta.getInt(1));
                habitacion.setNombre(cursorConsulta.getString(2));
                habitacion.setDescripcion(cursorConsulta.getString(3).replace("\\n", "\n"));
                habitacion.setPrecioNoche(cursorConsulta.getDouble(4));
                habitacion.setImagen(cursorConsulta.getString(5));
                habitacion.setEstado(cursorConsulta.getInt(6));
                try {
                    habitacion.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(7)));
                    habitacion.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(8)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaHabitaciones.add(habitacion);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaHabitaciones;
    }

    /**
     * obtenerPorId: Se encarga de llamar al metodo leerPorId en la DAC, para poder obtener la información de una habitación específica.
     * De esta manera, enviía dicha informacion a la entidad habitación.
     * @param idHabitacion
     * @return habitacion
     * @throws AppException
     */
    public Habitacion obtenerPorId(int idHabitacion) throws AppException {
        Habitacion habitacion = new Habitacion();
        cursorConsulta = habitacionDAC.leerPorId(idHabitacion);

        if (cursorConsulta.moveToFirst()) {
            habitacion = new Habitacion();
            habitacion.setId(cursorConsulta.getInt(0));
            habitacion.setIdHotel(cursorConsulta.getInt(1));
            habitacion.setNombre(cursorConsulta.getString(2));
            habitacion.setDescripcion(cursorConsulta.getString(3));
            habitacion.setPrecioNoche(cursorConsulta.getDouble(4));
            habitacion.setImagen(cursorConsulta.getString(5));
            habitacion.setEstado(cursorConsulta.getInt(6));
            try {
                habitacion.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(7)));
                habitacion.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(8)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerPorId()");
            }
        }

        return habitacion;
    }



}
