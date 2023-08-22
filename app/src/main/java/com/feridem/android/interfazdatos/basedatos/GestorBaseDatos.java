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
    private Context contexto;
    private String rutaBD;

    public GestorBaseDatos(@Nullable Context contexto) {
        super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
        this.contexto = contexto;
        this.rutaBD = contexto.getDatabasePath(BASEDATOS_NOMBRE).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_USUARIO + "(" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "nombre TEXT NOT NULL," +
//                "correo TEXT NOT NULL," +
//                "celular TEXT NOT NULL," +
//                "contrasenia TEXT NOT NULL)");
//        Log.i("mensaje error", "CREANDO base datos");
//        if (!existeBaseDatos()) {
//             Si la base de datos no existe, la copiamos desde assets
//            copiarBaseDatos();
//            Log.i("mensaje error", "NO existe base datos");
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_USUARIO);
        onCreate(sqLiteDatabase);
    }
    public void openDatabase() {
        String outputPath = contexto.getDatabasePath(BASEDATOS_NOMBRE).getPath();
        SQLiteDatabase.openDatabase(outputPath, null, 0);
    }

    public void copiarBaseDatos() {
        this.getReadableDatabase();
        try {
            InputStream inputStream = contexto.getAssets().open(BASEDATOS_NOMBRE);
            OutputStream outputStream = new FileOutputStream(rutaBD);
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
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(rutaBD, null, 0);
        } catch (Exception e) {
            Log.i("mensaje feridem", e.getMessage());
        }
        if (checkDB != null) {
            Log.i("mensaje feridem", "BASE de datos existente");
        } else {
            copiarBaseDatos();
        }
    }

}
