package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.NovaSolicitacaoDao;
import model.entities.NovaSolicitacao;

public class NovaSolicitacaoService {
	
	private NovaSolicitacaoDao dao = DaoFactory.createNovaSolicitacaoDao();
	
	public List<NovaSolicitacao> findAll() {
		return null;
	}

	public void saveOrUpdate(NovaSolicitacao obj, int idUsuario) {
		dao.insert(obj, idUsuario);
	}
}