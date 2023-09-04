package com.feridem.android.logicanegocio;

import android.content.Context;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;



public class ValidarDatos {
    /**
     * Verifica que un campo de texto no este vacio
     * @param ingresar
     * @param contexto
     * @return true si el campo no esta lleno y false si esta vacio
     */
    public static boolean campoLleno(Context contexto, EditText ingresar) {
        String textoIngresar = ingresar.getText().toString();
        if (textoIngresar.trim().length() == 0) {
            Toast.makeText(contexto, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Metodo que valida el ingreso de un contrasenia y que se confirme la misma contrasenia
     * @param contrasenia
     * @param confirmar
     * @param contexto
     * @return
     */
    public static boolean confirmarContrasenia(Context contexto, EditText contrasenia, EditText confirmar) {
        String textoContrasenia = contrasenia.getText().toString();
        String textoConfirmar = confirmar.getText().toString();
        if (textoContrasenia.equals(textoConfirmar))
            return true;
        Toast.makeText(contexto, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Valida que no se ingrese una cadena de caracteres muy pequenia o muy grande y muestra un mensaje
     * @param contexto
     * @param ingresarTexto
     * @param tipoDato
     * @param longitudMax
     * @param longitudMin
     * @return true si es si la longitud del texto se encuentra en el rango indicado
     */
    public static boolean longitudTextoMaxMin(Context contexto, EditText ingresarTexto, String tipoDato, int longitudMin, int longitudMax) {
        String texto = ingresarTexto.getText().toString();
        String mensaje = tipoDato + ((texto.length() > longitudMax)? " no debe exceder los " + longitudMax + " caracteres" : " debe tener al menos " + longitudMin + " caracteres");

        if (texto.length() <= longitudMax && texto.length() >= longitudMin)
            return true;

        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Valida que se ingrese un numero de celular de n digitos
     * @param contexto
     * @param ingresarCelular
     * @param cantNumeros
     * @return
     */
    public static boolean longitudCelular(Context contexto, EditText ingresarCelular, int cantNumeros) {
        String numCelular = ingresarCelular.getText().toString();
        String mensaje = "El número de celular no es válido";

        if (numCelular.length() == cantNumeros && numCelular.substring(0, 2).equals("09"))
            return true;

        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        return false;
    }


    /**
     * Valida que se ingres un correo válido
     * @param contexto
     * @param ingresarCorreo
     * @return true si el correo es valido y false si no es valido
     */
    public static boolean validarCorreo(Context contexto, EditText ingresarCorreo) {
        String textoCorreo = ingresarCorreo.getText().toString();
        String mensaje = "El correo ingresado no es válido";
        String patronCorreo = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern patron = Pattern.compile(patronCorreo);

        if (patron.matcher(textoCorreo).matches())
            return true;

        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        return false;
    }




}