package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Esta es la base de datos
 */
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

    /**
     * onCreate: Crea la base de datos.
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    /**
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void abrirBaseDatos() {
        String outputPath = contexto.getDatabasePath(BASEDATOS_NOMBRE).getPath();
        SQLiteDatabase.openDatabase(outputPath, null, 0);
    }

    /**
     * copiarBaseDatos:  se utiliza para copiar una base de datos desde los activos de la aplicación a una ubicación en el dispositivo,
     * lo que permite a la aplicación acceder y utilizar la base de datos en su funcionamiento.
     * @throws AppException
     */
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

    /**
     * comprobarBaseDatos:  comprueba si la base de datos existe y puede abrirse en la ubicación especificada.
     * @throws AppException
     */
    public void comprobarBaseDatos() throws AppException {
        SQLiteDatabase sqliteBaseDatos = null;
        try {
            sqliteBaseDatos = SQLiteDatabase.openDatabase(rutaBaseDatos, null, 0);
        } catch (SQLException error) {
        }
        if (sqliteBaseDatos == null) {
            copiarBaseDatos();
        }
    }

    /**
     * obtenerConsulta:  facilita la ejecución de consultas SQL en la base de datos
     * y devuelve un Cursor que permite acceder a los resultados de la consulta.
     * @param consultaSQL
     * @param valores
     * @return
     * @throws AppException
     */
    protected Cursor obtenerConsulta(String consultaSQL, String[] valores) throws AppException {
        try {
            cursorConsulta = getReadableDatabase().rawQuery(consultaSQL, valores);
            return cursorConsulta;
        } catch (SQLException error) {
            throw new AppException(error, getClass(), "obtenerConsulta()");
        }
    }


    public abstract Cursor leerRegistros() throws AppException;

    public abstract Cursor leerPorId(int idRegistro) throws AppException;


}
