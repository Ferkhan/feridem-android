package com.feridem.android.framework;

import android.util.Log;

public class AppException extends Exception {

    public AppException(Throwable cause, Object className, String mesagge) {
        super(className + ": " + mesagge, cause);
        establecerRegistroDepuracion();
    }
    
    public AppException(Throwable cause, Object className) {
        super(className + ": Error no especificado", cause);
        establecerRegistroDepuracion();
    }
    
    public AppException(String mesagge) {
        super("Error en clase no especificada: " + mesagge);
        establecerRegistroDepuracion();
    }

    void establecerRegistroDepuracion() {
        String mensajeLog = "{AppException}\n"
                            + getMessage() + "\n"
                            + getCause();
        Log.i("AppExeption", mensajeLog);
    }
    
}
