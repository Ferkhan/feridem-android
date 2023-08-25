package com.feridem.android.logicanegocio;

import android.widget.Toast;

import com.feridem.android.interfazdatos.basedatos.GestorUsuario;
import com.feridem.android.interfazdatos.basedatos.GestorUsuarioCredencial;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.entidades.UsuarioCredencial;

import android.content.Context;
import java.util.ArrayList;

public class ValidarIniciarSesion {

    private Context contexto;
    private GestorUsuario gestorUsuario;
    private GestorUsuarioCredencial gestorCredencial;
    public ValidarIniciarSesion(Context contexto) {
        this.contexto = contexto;
        this.gestorUsuario = new GestorUsuario(contexto);
        this.gestorCredencial = new GestorUsuarioCredencial(contexto);

    }

    public boolean validarCuentaUsuario(String ingresarCorreo, String ingresarContrasena) {
        ArrayList<Usuario> listaUsuarios = gestorUsuario.leerUsuarios();
        ArrayList<UsuarioCredencial> listaCredenciales = gestorCredencial.leerCredenciales();
        for (Usuario usuario : listaUsuarios)
            if (ingresarCorreo.equals(usuario.getCorreo())) {
                for (UsuarioCredencial credencial : listaCredenciales)
                    if (usuario.getId() == credencial.getId() && ingresarContrasena.equals(credencial.getContrasena())) {
                        Toast.makeText(contexto, "¡Saludos, " + usuario.getNombre() + "!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
            }

        Toast.makeText(contexto, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        return false;
    }

}
