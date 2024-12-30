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
}