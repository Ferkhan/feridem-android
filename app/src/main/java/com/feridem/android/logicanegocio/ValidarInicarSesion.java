package com.feridem.android.logicanegocio;

import android.widget.Toast;

import com.feridem.android.interfazdatos.modeloentidad.Usuario;
import android.content.Context;
import java.util.ArrayList;

public class ValidarInicarSesion {

    ArrayList<Usuario> listaUsuarios;
    Context contexto;
    public ValidarInicarSesion(Context contexto, ArrayList<Usuario> listaUsuarios) {
        this.contexto = contexto;
        this.listaUsuarios = listaUsuarios;
    }

    public boolean validarCuentaUsuario(String ingresarCorreo, String ingresarContrasenia) {
        for (Usuario usuario : listaUsuarios)
            if ((ingresarCorreo.equals(usuario.getCorreo())) && ingresarContrasenia.equals(usuario.getContrasena())) {
                Toast.makeText(contexto, "¡Saludos, " + usuario.getNombre() + "!", Toast.LENGTH_SHORT).show();
                return true;
            }

        Toast.makeText(contexto, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        return false;
    }

}
