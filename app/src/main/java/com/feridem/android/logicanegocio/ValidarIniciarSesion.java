package com.feridem.android.logicanegocio;

import android.widget.Toast;

import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.fachada.UsuarioBL;
import com.feridem.android.logicanegocio.fachada.UsuarioCredencialBL;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.entidades.UsuarioCredencial;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class ValidarIniciarSesion {

    private Context contexto;
    private UsuarioBL usuarioBL;
    private UsuarioCredencialBL gestorCredencial;
    public ValidarIniciarSesion(Context contexto) {
        this.contexto = contexto;
        this.usuarioBL = new UsuarioBL(contexto);
        this.gestorCredencial = new UsuarioCredencialBL(contexto);

    }

    public boolean validarCuentaUsuario(String ingresarCorreo, String ingresarContrasena) throws AppException {
        List<Usuario> listaUsuarios = usuarioBL.obtenerRegistrosActivos();
        UsuarioCredencial usuarioCredencial;

        for (Usuario usuario : listaUsuarios)
            if (ingresarCorreo.equals(usuario.getCorreo())) {
                usuarioCredencial = gestorCredencial.obtenerPorId(usuario.getId());
                if (ingresarContrasena.equals(usuarioCredencial.getContrasena())) {
                    Toast.makeText(contexto, "¡Saludos, " + usuario.getNombre() + "!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }

        Toast.makeText(contexto, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        return false;
    }

}
