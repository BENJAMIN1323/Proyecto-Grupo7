package s2.proyecto.model;

public class Autenticacion {

    private static Usuario usuarioAutenticado;

    public static Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public static void setUsuarioAutenticado(Usuario usuario) {
        usuarioAutenticado = usuario;
    }
}
