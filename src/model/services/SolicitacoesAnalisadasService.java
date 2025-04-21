package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SolicitacoesAnalisadasDao;
import model.entities.SolicitacoesAnalisadas;
import model.entities.StatusSolicitacao;

public class SolicitacoesAnalisadasService {
	
	private SolicitacoesAnalisadasDao dao = DaoFactory.createSolicitacoesAnalisadasDao();
	
	public List<SolicitacoesAnalisadas> select(StatusSolicitacao status, int idTipoUsuario, int idUsuario) {
		return dao.select(status, idTipoUsuario, idUsuario);
	}
}