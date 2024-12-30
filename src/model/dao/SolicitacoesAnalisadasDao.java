package model.dao;

import java.util.List;

import model.entities.SolicitacoesAnalisadas;
import model.entities.StatusSolicitacao;

public interface SolicitacoesAnalisadasDao {

	List<SolicitacoesAnalisadas> select(StatusSolicitacao status, int idTipoUsuario, int idUsuario);
}
