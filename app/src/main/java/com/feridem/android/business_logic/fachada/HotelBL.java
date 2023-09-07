package com.feridem.android.business_logic.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.data_access.HotelDAC;
import com.feridem.android.business_logic.entidades.Hotel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponde a la lógica de negocios de hotel.
 */
public class HotelBL extends GestorBL {
    private Hotel hotel;
    private HotelDAC hotelDAC;
    public HotelBL(Context contexto) {
        super(contexto);
        hotelDAC = new HotelDAC(contexto);
        hotel = new Hotel();
    }

    /**
     * obtenerRegistrosActivos:Se encarga de llamar a la lectura de registros en la DAC, para poder obtener los registros Activos.
     *  De esta manera, obtiene la información y la envía a la entidad habitacionReservada.
     * @return: listaHoteles: Representa el conjunto de hoteles.
     * @throws AppException
     */
    public List<Hotel> obtenerRegistrosActivos() throws AppException {
        List<Hotel> listaHoteles = new ArrayList<>();
        cursorConsulta = hotelDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                hotel = new Hotel();
                hotel.setId(cursorConsulta.getInt(0));
                hotel.setNombre(cursorConsulta.getString(1));
                hotel.setCiudad(cursorConsulta.getString(2));
                hotel.setDireccion(cursorConsulta.getString(3));
                hotel.setLatitud(cursorConsulta.getDouble(4));
                hotel.setLongitud(cursorConsulta.getDouble(5));
                hotel.setEstado(cursorConsulta.getInt(6));
                try {
                    hotel.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(7)));
                    hotel.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(8)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaHoteles.add(hotel);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaHoteles;
    }

    /**
     * obtenerPorId: Se encarga de llamar al metodo leerPorId en la DAC, para poder obtener la información de un hotel especifíco.
     * De esta manera, enviía dicha informacion a la entidad hotel.
     * @param idHotel
     * @return hotel.
     * @throws AppException
     */
    public Hotel obtenerPorId(int idHotel) throws AppException {
        cursorConsulta = hotelDAC.leerPorId(idHotel);

        if (cursorConsulta.moveToFirst()) {
            hotel = new Hotel();
            hotel.setId(cursorConsulta.getInt(0));
            hotel.setNombre(cursorConsulta.getString(1));
            hotel.setCiudad(cursorConsulta.getString(2));
            hotel.setDireccion(cursorConsulta.getString(3));
            hotel.setLatitud(cursorConsulta.getDouble(4));
            hotel.setLongitud(cursorConsulta.getDouble(5));
            hotel.setEstado(cursorConsulta.getInt(6));
            try {
                hotel.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(7)));
                hotel.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(8)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerPorId()");
            }
        }

        return hotel;
    }


}
