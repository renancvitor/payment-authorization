package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.SolicitacoesAnalisadasDao;
import model.entities.SolicitacoesAnalisadas;
import model.entities.StatusSolicitacao;

public class SolicitacoesAnalisadasDaoJDBC implements SolicitacoesAnalisadasDao {
	
	private Connection connection;
	
	public SolicitacoesAnalisadasDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<SolicitacoesAnalisadas> select(StatusSolicitacao status, int idTipoUsuario, int idUsuario) {
		List<SolicitacoesAnalisadas> solicitacoes = new ArrayList<>();
        String sql;

        if (idTipoUsuario == 4) {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE status IN ('APROVADA', 'REPROVADA') " +
                    "AND id_usuario = ?";
        } else if (idTipoUsuario == 3) {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE (s.status = 'APROVADA' OR (s.status = 'REPROVADA' AND s.id_usuario = ?))";
        } else {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE s.status IN ('APROVADA', 'REPROVADA')";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (idTipoUsuario == 4 || idTipoUsuario == 3) {
                stmt.setInt(1, idUsuario);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	SolicitacoesAnalisadas solicitacao = new SolicitacoesAnalisadas(
                        rs.getInt("id"),
                        rs.getString("fornecedor"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_criacao"),
                        rs.getDate("data_pagamento"),
                        rs.getString("forma_pagamento"),
                        rs.getDouble("valor_total"),
                        rs.getInt("id_usuario"),
                        StatusSolicitacao.valueOf(rs.getString("status")),
                        rs.getString("login")
                );
                solicitacoes.add(solicitacao);
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return solicitacoes;
	}
}