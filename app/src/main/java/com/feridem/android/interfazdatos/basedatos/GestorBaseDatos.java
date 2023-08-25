package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class GestorBaseDatos extends SQLiteOpenHelper {
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
        } catch (Exception e) {
            Log.i("mensaje feridem", e.getMessage());
        }
        try {
            abrirBaseDatos();
        } catch (Exception e) {
            Log.i("mensaje feridem", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void abrirBaseDatos() {
        String outputPath = contexto.getDatabasePath(BASEDATOS_NOMBRE).getPath();
        SQLiteDatabase.openDatabase(outputPath, null, 0);
    }

    public void copiarBaseDatos() {
        this.getReadableDatabase();
        try {
            InputStream inputStream = contexto.getAssets().open(BASEDATOS_NOMBRE);
            OutputStream outputStream = new FileOutputStream(rutaBaseDatos);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            Log.i("mensaje feridem", "EXITO al copiar base de datos");
        } catch (Exception e) {
            Log.i("mensaje feridem", "ERROR al copiar base de datos");
        }
    }

    public void comprobarBaseDatos() {
        SQLiteDatabase sqliteBaseDatos = null;
        try {
            sqliteBaseDatos = SQLiteDatabase.openDatabase(rutaBaseDatos, null, 0);
        } catch (Exception e) {
            Log.i("mensaje feridem", e.getMessage());
        }
        if (sqliteBaseDatos != null) {
            Log.i("mensaje feridem", "BASE de datos existente");
        } else {
            copiarBaseDatos();
        }
    }

}
