package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SolicitacoesEnviadasDao;
import model.entities.SolicitacoesEnviadas;

public class SolicitacoesEnviadasService {
	
	private SolicitacoesEnviadasDao dao = DaoFactory.createSolicitacoesEnviadasDao();
	
	public List<SolicitacoesEnviadas> findAll(int idTipoUsuario, int idUser) {
		return dao.findAll(idTipoUsuario, idUser);
	}
}