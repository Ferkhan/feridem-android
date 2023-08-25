package com.feridem.android.logicanegocio;

import android.widget.Toast;

import com.feridem.android.logicanegocio.fachada.UsuarioBL;
import com.feridem.android.logicanegocio.fachada.UsuarioCredencialBL;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.entidades.UsuarioCredencial;

import android.content.Context;
import java.util.ArrayList;

public class ValidarIniciarSesion {

    private Context contexto;
    private UsuarioBL usuarioBL;
    private UsuarioCredencialBL gestorCredencial;
    public ValidarIniciarSesion(Context contexto) {
        this.contexto = contexto;
        this.usuarioBL = new UsuarioBL(contexto);
        this.gestorCredencial = new UsuarioCredencialBL(contexto);

    }

    public boolean validarCuentaUsuario(String ingresarCorreo, String ingresarContrasena) {
        ArrayList<Usuario> listaUsuarios = usuarioBL.leerUsuarios();
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
