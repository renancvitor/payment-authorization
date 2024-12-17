package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioService {
	
	private UsuarioDao dao = DaoFactory.createUsuarioDao();
	
	public List<Usuario> findAll() {
		return dao.findAll();
	}

}
