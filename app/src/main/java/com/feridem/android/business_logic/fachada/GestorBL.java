package com.feridem.android.business_logic.fachada;

import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;

/**
 * Es el gestor para la parte de la lógica de negocios.
 */
public abstract class GestorBL {
    protected Context contexto;
    protected Cursor cursorConsulta;
    protected SimpleDateFormat formatoFecha;
    protected SimpleDateFormat formatoFechaHora;

    public GestorBL(Context contexto) {
        this.contexto = contexto;
        formatoFecha     = new SimpleDateFormat("yyyy-MM-dd");
        formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


}
