package com.feridem.android.logicanegocio;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class ValidarDatos {
    public static boolean campoLleno(EditText ingresar, Context contexto) {
        String textoIngresar = ingresar.getText().toString();
        if (textoIngresar.length() == 0) {
            Toast.makeText(contexto, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean confirmarContrasenia(EditText contrasenia, EditText confirmar, Context contexto) {
        String textoContrasenia = contrasenia.getText().toString();
        String textoConfirmar = confirmar.getText().toString();
        if (textoContrasenia.equals(textoConfirmar))
            return true;
        Toast.makeText(contexto, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        return false;
    }

}
