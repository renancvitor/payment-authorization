package model.dao;

import java.util.List;

import model.entities.SolicitacoesEnviadas;
import model.entities.StatusSolicitacao;

public interface SolicitacoesEnviadasDao {
	
	int countAll(StatusSolicitacao status);
	List<SolicitacoesEnviadas>findAll(int idTipoUsuario, int idUser);
	void update(SolicitacoesEnviadas obj);
}