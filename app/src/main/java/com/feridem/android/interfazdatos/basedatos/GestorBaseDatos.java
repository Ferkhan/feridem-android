package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class GestorBaseDatos extends SQLiteOpenHelper {
    private static final int BASEDATOS_VERSION = 1;
    private static final String BASEDATOS_NOMBRE = "FeridemBD.db";
    protected static final String TABLA_USUARIO = "USUARIO";
    protected static final String TABLA_HOTEL = "HOTEL";
    protected static final String TABLA_USUARIO_CREDENCIAL = "USUARIO_CREDENCIAL";
    protected static final String TABLA_HABITACION_IMAGEN = "HABITACION_IMAGEN";
    protected static final String TABLA_HABITACION_RESERVADA = "HABITACION_RESERVADA";
    protected static final String TABLA_REGISTRO_SESION = "REGISTRO_SESION";
    protected static final String TABLA_USUARIO_ROL = "USUARIO_ROL";
    protected static final String TABLA_HABITACION = "HABITACION";
    protected Context contexto;
    protected ContentValues valoresContenido;
    protected String consultaSQL;
    protected Cursor cursorConsulta;
    private String rutaBaseDatos;

    public GestorBaseDatos(@Nullable Context contexto) {
        super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
        this.contexto = contexto;
        this.rutaBaseDatos = contexto.getDatabasePath(BASEDATOS_NOMBRE).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            comprobarBaseDatos();
        } catch (Exception error) {
            Log.i("FeridemException", error.getMessage());
        }
        try {
            abrirBaseDatos();
        } catch (Exception error) {
            Log.i("FeridemException", error.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void abrirBaseDatos() {
        String outputPath = contexto.getDatabasePath(BASEDATOS_NOMBRE).getPath();
        SQLiteDatabase.openDatabase(outputPath, null, 0);
    }

    public void copiarBaseDatos() throws AppException {
        this.getReadableDatabase();
        try {
            InputStream inputStream = contexto.getAssets().open(BASEDATOS_NOMBRE);
            OutputStream outputStream = new FileOutputStream(rutaBaseDatos);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception error) {
            throw new AppException(error, getClass(), "copiarBaseDatos()");
        }
    }

    public void comprobarBaseDatos() throws AppException {
        SQLiteDatabase sqliteBaseDatos = null;
        try {
            sqliteBaseDatos = SQLiteDatabase.openDatabase(rutaBaseDatos, null, 0);
        } catch (SQLException error) {
            throw new AppException(error, getClass(), "comprobarBaseDatos()");
        }
        if (sqliteBaseDatos == null) {
            copiarBaseDatos();
        }
    }

    protected Cursor obtenerConsulta(String consultaSQL, String[] valores) throws AppException {
        try {
            cursorConsulta = getReadableDatabase().rawQuery(consultaSQL, valores);
            return cursorConsulta;
        } catch (SQLException error) {
            throw new AppException(error, getClass(), "obtenerConsulta()");
        }
    }


    public abstract Cursor leerRegistrosActivos() throws AppException;

    public abstract Cursor leerPorId(int idRegistro) throws AppException;

//    public abstract long insertarRegistro();

}
