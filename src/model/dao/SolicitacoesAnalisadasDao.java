package model.dao;

import java.util.List;

import model.entities.SolicitacoesAnalisadas;

public interface SolicitacoesAnalisadasDao {

	List<SolicitacoesAnalisadas> select(int idTipoUsuario, int idUsuario);
}
