package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GestorBaseDatos extends SQLiteOpenHelper {
    private static final int BASEDATOS_VERSION = 1;
    private static final String BASEDATOS_NOMBRE = "FeridemBD.db";
    protected static final String TABLA_USUARIO = "USUARIO";
    protected static final String TABLA_HABITACION = "HABITACION";
    protected static final String TABLA_HOTEL = "HOTEL";
    private Context contexto;
    String rutaBaseDatos;

    public GestorBaseDatos(@Nullable Context contexto) {
        super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_USUARIO + "(" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "nombre TEXT NOT NULL," +
//                "correo TEXT NOT NULL," +
//                "celular TEXT NOT NULL," +
//                "contrasenia TEXT NOT NULL)");

        if (!existeBaseDatos()) {
            // Si la base de datos no existe, la copiamos desde assets
            copiarBaseDatos();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_USUARIO);
        onCreate(sqLiteDatabase);
    }

    private void copiarBaseDatos() {
        rutaBaseDatos = contexto.getApplicationContext().getDatabasePath(BASEDATOS_NOMBRE).getPath();
        try {
            InputStream inputStream = contexto.getApplicationContext().getAssets().open(BASEDATOS_NOMBRE);
            OutputStream outputStream = new FileOutputStream(rutaBaseDatos);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean existeBaseDatos() {
        File dbFile = contexto.getApplicationContext().getDatabasePath(BASEDATOS_NOMBRE);
        return dbFile.exists();
    }

}
