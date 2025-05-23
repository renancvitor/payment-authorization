package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class FuncionarioService {
	
	private PessoaDao dao = DaoFactory.createPessoaDao();
	
	public List<Pessoa> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Pessoa obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
}