package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SolicitacoesAnalisadasDao;
import model.entities.SolicitacoesAnalisadas;

public class SolicitacoesAnalisadasService {
	
	private SolicitacoesAnalisadasDao dao = DaoFactory.createSolicitacoesAnalisadasDao();
	
	public List<SolicitacoesAnalisadas> select(int idTipoUsuario, int idUsuario) {
		return dao.select(idTipoUsuario, idUsuario);
	}

}
