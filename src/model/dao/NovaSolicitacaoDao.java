package model.dao;

import model.entities.NovaSolicitacao;

public interface NovaSolicitacaoDao {

	void insert(NovaSolicitacao obj, int idUsuario);
	void update(NovaSolicitacao obj);
}
