package model.services;

import model.entities.Usuario;

public class UserLoginService {
	private static Usuario usuarioLogado;

	public static void setUsuarioLogado(Usuario usuario) {
		usuarioLogado = usuario;
	}
	
	public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
	
	public static boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }
	
	public static void logout() {
        usuarioLogado = null;
    }
}