package com.feridem.android.business_logic.utilities;

import android.widget.Toast;

import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.managers.RegistroSesionBL;
import com.feridem.android.business_logic.managers.UsuarioBL;
import com.feridem.android.business_logic.managers.UsuarioCredencialBL;
import com.feridem.android.business_logic.entities.Usuario;
import com.feridem.android.business_logic.entities.UsuarioCredencial;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Corresponde a una clase para poder verificar los datos del usuario.
 */
public class VerificarDatos {

    private Context contexto;
    private UsuarioBL usuarioBL;
    private UsuarioCredencialBL gestorCredencial;
    private RegistroSesionBL registroSesionBL;

    public VerificarDatos(Context contexto) {
        this.contexto = contexto;
        this.usuarioBL = new UsuarioBL(contexto);
        this.gestorCredencial = new UsuarioCredencialBL(contexto);
        this.registroSesionBL = new RegistroSesionBL(contexto);

    }

    /**
     * verificarCuentaUsuario: verifica las credenciales de un usuario durante el inicio de sesion,
     * buscando un usuario con el correo proporcionado y luego verificando si la contrasenia coincide.
     * @param ingresarCorreo
     * @param ingresarContrasenia
     * @return
     * @throws AppException
     */
    public boolean verificarCuentaUsuario(String ingresarCorreo, String ingresarContrasenia) throws AppException {
        List<Usuario> listaUsuarios = usuarioBL.obtenerRegistrosActivos();
        UsuarioCredencial usuarioCredencial;

        for (Usuario usuario : listaUsuarios)
            if (ingresarCorreo.equals(usuario.getCorreo())) {
                usuarioCredencial = gestorCredencial.obtenerPorId(usuario.getId());
                if (verificarContrasenia(ingresarContrasenia, usuarioCredencial.getContrasena())) {
                    Toast.makeText(contexto, "¡Saludos, " + usuario.getNombre() + "!", Toast.LENGTH_SHORT).show();
                    registroSesionBL.conectarUsuario(usuario.getId(), "OK", 1);
                    return true;
                }
            }

        Toast.makeText(contexto, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * encriptarContrasenia:este método toma una contrasenia en formato de texto claro, la encripta
     * utilizando el algoritmo de resumen MD5 y devuelve la representacion hexadecimal de la
     * contrasenia encriptada.
     * @param contrasenia
     * @return un string con la contrasenia encriptada
     * @throws AppException
     */
    public String encriptarContrasenia(String contrasenia) throws AppException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(contrasenia.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException error) {
            throw new AppException(error, getClass(), "encriptarContrasenia()");
        }
    }

    /**
     * verificarContrasenia:este metodo se utiliza para verificar si una contraseña proporcionada
     * coincide con la contrasenia almacenada despues de aplicar el mismo proceso de encriptacion.
     * @param contrasenia
     * @param contraseniaEncriptada
     * @return
     * @throws AppException
     */
    public boolean verificarContrasenia(String contrasenia, String contraseniaEncriptada) throws AppException {
        contrasenia = encriptarContrasenia(contrasenia);
        return contrasenia.equals(contraseniaEncriptada);
    }
}
