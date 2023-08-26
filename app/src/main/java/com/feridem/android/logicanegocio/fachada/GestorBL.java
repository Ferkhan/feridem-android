package com.feridem.android.logicanegocio.fachada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;

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
