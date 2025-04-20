package model.services;

import java.util.List;

import model.dao.AlterarSenhaDao;
import model.dao.DaoFactory;
import model.entities.AlterarSenha;
import model.entities.Usuario;

public class AlterarSenhaService {
	
	private AlterarSenhaDao dao = DaoFactory.createAlterarSenhaDao();
	
	public List<AlterarSenha> findAll() {
		return null;
	}
	
	public boolean saveOrUpdate(String username, String novaSenha) {
		return dao.update(username, novaSenha);
	}

}
