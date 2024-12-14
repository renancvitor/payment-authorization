package model.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.entities.UserType;
import model.entities.Usuario;

public interface LoginDao {
	
	Usuario selectUser(String login, String senha) throws NoSuchAlgorithmException;
	List<String> getPermissoesByUsuarioId(int usuarioId);
	UserType getUserTypeFromId(int idTipoUsuario);
}
